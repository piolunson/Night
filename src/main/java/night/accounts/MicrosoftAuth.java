package night.accounts;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Microsoft / Xbox Live / Minecraft authentication for the alt manager.
 *
 * <p>The flow is the public OAuth 2.0 device-code flow, so no client secret is
 * shipped with the mod:</p>
 * <ol>
 *     <li>request a device code and show the user the verification URL,</li>
 *     <li>poll the token endpoint until the user finishes signing in,</li>
 *     <li>exchange the Microsoft token for an Xbox Live token and XSTS ticket,</li>
 *     <li>exchange the XSTS ticket for a Minecraft access token,</li>
 *     <li>read the Minecraft profile (name + UUID).</li>
 * </ol>
 *
 * <p>A device-code capable Azure application (public client) is required. Its id
 * is read from the {@code night.msa.clientId} system property so that no
 * credential is ever compiled into the mod.</p>
 */
public final class MicrosoftAuth {
    private static final String CLIENT_ID = System.getProperty("night.msa.clientId", "").trim();
    private static final String SCOPE = "XboxLive.signin offline_access";
    private static final String DEVICE_CODE_URL = "https://login.microsoftonline.com/consumers/oauth2/v2.0/devicecode";
    private static final String TOKEN_URL = "https://login.microsoftonline.com/consumers/oauth2/v2.0/token";
    private static final String XBL_URL = "https://user.auth.xboxlive.com/user/authenticate";
    private static final String XSTS_URL = "https://xsts.auth.xboxlive.com/xsts/authorize";
    private static final String MC_LOGIN_URL = "https://api.minecraftservices.com/authentication/login_with_xbox";
    private static final String MC_PROFILE_URL = "https://api.minecraftservices.com/minecraft/profile";
    private static final Gson GSON = new Gson();
    private static final HttpClient HTTP = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10L)).build();
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "Night-MicrosoftAuth");
        thread.setDaemon(true);
        return thread;
    });

    private MicrosoftAuth() {
    }

    /** Result of a completed (or failed) interactive login. */
    public record LoginResult(boolean success, String username, UUID uuid, String refreshToken, String accessToken, String errorMessage) {
        public static LoginResult failure(String message) {
            return new LoginResult(false, null, null, null, null, message);
        }
    }

    /** A login that is running in the background while the GUI polls its state. */
    public static final class BrowserLoginSession {
        private volatile String loginUrl;
        private volatile Runnable cancelAction;
        private final CompletableFuture<LoginResult> future = new CompletableFuture<LoginResult>();

        public String loginUrl() {
            return this.loginUrl;
        }

        public CompletableFuture<LoginResult> future() {
            return this.future;
        }

        /** Optional hook the GUI can run when the login dialog is closed. */
        public Runnable cancelAction() {
            return this.cancelAction;
        }
    }

    /**
     * Starts the device-code login. The returned session is usable immediately;
     * {@link BrowserLoginSession#loginUrl()} becomes available once Microsoft has
     * answered and the future completes when the whole chain has finished.
     */
    public static BrowserLoginSession startBrowserLogin(Consumer<String> status) {
        BrowserLoginSession session = new BrowserLoginSession();
        Consumer<String> report = message -> {
            if (status != null) {
                status.accept(message);
            }
        };
        if (CLIENT_ID.isEmpty()) {
            session.future.complete(LoginResult.failure("Microsoft login is not configured: start the game with -Dnight.msa.clientId=<azure app id>."));
            return session;
        }
        EXECUTOR.submit(() -> {
            try {
                report.accept("Requesting Microsoft device code...");
                Map<String, String> deviceRequest = new LinkedHashMap<String, String>();
                deviceRequest.put("client_id", CLIENT_ID);
                deviceRequest.put("scope", SCOPE);
                JsonObject device = postForm(DEVICE_CODE_URL, deviceRequest, false);
                String deviceCode = device.get("device_code").getAsString();
                String userCode = device.get("user_code").getAsString();
                String verificationUri = device.get("verification_uri").getAsString();
                String completeUri = device.has("verification_uri_complete") ? device.get("verification_uri_complete").getAsString() : verificationUri;
                session.loginUrl = completeUri;
                report.accept("Enter the code " + userCode + " at " + verificationUri);
                long intervalMs = Math.max(2L, device.has("interval") ? device.get("interval").getAsLong() : 5L) * 1000L;
                long deadline = System.currentTimeMillis() + Math.max(60L, device.has("expires_in") ? device.get("expires_in").getAsLong() : 900L) * 1000L;
                String msAccessToken = null;
                String msRefreshToken = "";
                while (System.currentTimeMillis() < deadline) {
                    Thread.sleep(intervalMs);
                    Map<String, String> tokenRequest = new LinkedHashMap<String, String>();
                    tokenRequest.put("grant_type", "urn:ietf:params:oauth:grant-type:device_code");
                    tokenRequest.put("client_id", CLIENT_ID);
                    tokenRequest.put("device_code", deviceCode);
                    JsonObject token = postForm(TOKEN_URL, tokenRequest, true);
                    if (token.has("access_token")) {
                        msAccessToken = token.get("access_token").getAsString();
                        if (token.has("refresh_token")) {
                            msRefreshToken = token.get("refresh_token").getAsString();
                        }
                        break;
                    }
                    String error = token.has("error") ? token.get("error").getAsString() : "unknown_error";
                    if ("authorization_pending".equals(error) || "slow_down".equals(error)) {
                        continue;
                    }
                    if ("authorization_declined".equals(error)) {
                        throw new IOException("Login was declined in the browser.");
                    }
                    if ("expired_token".equals(error)) {
                        throw new IOException("The Microsoft device code expired, please try again.");
                    }
                    throw new IOException("Microsoft login failed: " + error);
                }
                if (msAccessToken == null) {
                    throw new IOException("Timed out waiting for the Microsoft login.");
                }
                report.accept("Signing in to Xbox Live...");
                String xblToken = xboxLiveAuthenticate(msAccessToken);
                report.accept("Authorizing with XSTS...");
                String[] xsts = xstsAuthorize(xblToken);
                report.accept("Signing in to Minecraft...");
                String minecraftToken = minecraftLogin(xsts[1], xsts[0]);
                report.accept("Reading Minecraft profile...");
                JsonObject profile = getJson(MC_PROFILE_URL, minecraftToken);
                String username = profile.get("name").getAsString();
                UUID uuid = uuidFromUndashed(profile.get("id").getAsString());
                session.future.complete(new LoginResult(true, username, uuid, msRefreshToken, minecraftToken, null));
            }
            catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                session.future.complete(LoginResult.failure("Login cancelled."));
            }
            catch (Exception exception) {
                String message = exception.getMessage();
                session.future.complete(LoginResult.failure(message == null || message.isBlank() ? exception.toString() : message));
            }
        });
        return session;
    }

    private static String xboxLiveAuthenticate(String microsoftAccessToken) throws IOException, InterruptedException {
        JsonObject properties = new JsonObject();
        properties.addProperty("AuthMethod", "RPS");
        properties.addProperty("SiteName", "user.auth.xboxlive.com");
        properties.addProperty("RpsTicket", "d=" + microsoftAccessToken);
        JsonObject body = new JsonObject();
        body.add("Properties", properties);
        body.addProperty("RelyingParty", "http://auth.xboxlive.com");
        body.addProperty("TokenType", "JWT");
        JsonObject response = postJson(XBL_URL, body, null);
        if (!response.has("Token")) {
            throw new IOException("Xbox Live rejected the Microsoft token.");
        }
        return response.get("Token").getAsString();
    }

    /** @return {@code [xstsToken, userHash]} */
    private static String[] xstsAuthorize(String xboxLiveToken) throws IOException, InterruptedException {
        JsonObject properties = new JsonObject();
        properties.addProperty("SandboxId", "RETAIL");
        com.google.gson.JsonArray userTokens = new com.google.gson.JsonArray();
        userTokens.add(xboxLiveToken);
        properties.add("UserTokens", userTokens);
        JsonObject body = new JsonObject();
        body.add("Properties", properties);
        body.addProperty("RelyingParty", "rp://api.minecraftservices.com/");
        body.addProperty("TokenType", "JWT");
        JsonObject response = postJson(XSTS_URL, body, null);
        if (!response.has("Token")) {
            long errorCode = response.has("XErr") ? response.get("XErr").getAsLong() : -1L;
            throw new IOException("XSTS authorization failed (XErr " + errorCode + ")" + xstsHint(errorCode));
        }
        String userHash = response.getAsJsonObject("DisplayClaims").getAsJsonArray("xui").get(0).getAsJsonObject().get("uhs").getAsString();
        return new String[]{response.get("Token").getAsString(), userHash};
    }

    private static String xstsHint(long errorCode) {
        if (errorCode == 2148916233L) {
            return ": this Microsoft account has no Xbox profile yet, create one at xbox.com.";
        }
        if (errorCode == 2148916235L) {
            return ": Xbox Live is not available in this account's country.";
        }
        if (errorCode == 2148916238L) {
            return ": this account is a child account and must be added to a family group.";
        }
        return "";
    }

    private static String minecraftLogin(String userHash, String xstsToken) throws IOException, InterruptedException {
        JsonObject body = new JsonObject();
        body.addProperty("identityToken", "XBL3.0 x=" + userHash + ";" + xstsToken);
        JsonObject response = postJson(MC_LOGIN_URL, body, null);
        if (!response.has("access_token")) {
            throw new IOException("Minecraft services refused the login (does this account own the game?).");
        }
        return response.get("access_token").getAsString();
    }

    private static JsonObject postForm(String url, Map<String, String> form, boolean allowErrorBody) throws IOException, InterruptedException {
        StringBuilder body = new StringBuilder();
        for (Map.Entry<String, String> entry : form.entrySet()) {
            if (body.length() > 0) {
                body.append('&');
            }
            body.append(java.net.URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8)).append('=').append(java.net.URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(20L)).header("Content-Type", "application/x-www-form-urlencoded").header("Accept", "application/json").POST(HttpRequest.BodyPublishers.ofString(body.toString())).build();
        return send(request, allowErrorBody);
    }

    private static JsonObject postJson(String url, JsonObject body, String bearer) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(20L)).header("Content-Type", "application/json").header("Accept", "application/json").POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(body), StandardCharsets.UTF_8));
        if (bearer != null) {
            builder.header("Authorization", "Bearer " + bearer);
        }
        return send(builder.build(), true);
    }

    private static JsonObject getJson(String url, String bearer) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(20L)).header("Accept", "application/json").GET();
        if (bearer != null) {
            builder.header("Authorization", "Bearer " + bearer);
        }
        return send(builder.build(), false);
    }

    private static JsonObject send(HttpRequest request, boolean allowErrorBody) throws IOException, InterruptedException {
        HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        String text = response.body() == null ? "" : response.body();
        JsonObject json = null;
        if (!text.isBlank()) {
            try {
                json = JsonParser.parseString(text).getAsJsonObject();
            }
            catch (RuntimeException ignored) {
                json = null;
            }
        }
        if (response.statusCode() >= 400 && !allowErrorBody) {
            throw new IOException("HTTP " + response.statusCode() + " from " + request.uri().getHost());
        }
        if (json == null) {
            throw new IOException("Unexpected response from " + request.uri().getHost());
        }
        return json;
    }

    private static UUID uuidFromUndashed(String value) {
        String hex = value.replace("-", "");
        if (hex.length() != 32) {
            throw new IllegalArgumentException("Malformed Minecraft uuid: " + value);
        }
        return new UUID(Long.parseUnsignedLong(hex.substring(0, 16), 16), Long.parseUnsignedLong(hex.substring(16), 16));
    }
}
