package night.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.network.chat.Component;
import night.accounts.Account;
import night.accounts.AccountType;
import night.utils.IMinecraft;
import night.mixins.accessors.MinecraftUserAccessor;

/**
 * Owns the list of known accounts, persists it under {@code <game dir>/night/accounts.json}
 * and swaps the active Minecraft session when an account is selected.
 *
 * <p>Offline accounts are created locally with the vanilla offline UUID
 * ({@code UUID.nameUUIDFromBytes("OfflinePlayer:" + name)}), so they behave exactly like a
 * cracked-launcher session. Microsoft accounts are stored with their Minecraft access token
 * and Microsoft refresh token.</p>
 */
public class AccountManager
implements IMinecraft {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final List<Account> accounts = new CopyOnWriteArrayList<Account>();
    private final Path storageFile;
    private volatile String currentName = "";

    public AccountManager() {
        this.storageFile = FabricLoader.getInstance().getGameDir().resolve("night").resolve("accounts.json");
        this.load();
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(new ArrayList<Account>(this.accounts));
    }

    public String getCurrentName() {
        if (this.currentName != null && !this.currentName.isEmpty()) {
            return this.currentName;
        }
        return Minecraft.getInstance().getUser() == null ? "" : Minecraft.getInstance().getUser().getName();
    }

    public Account addAccount(Account account) {
        if (account == null) {
            return null;
        }
        if (!this.accounts.contains(account)) {
            this.accounts.add(account);
        } else {
            this.accounts.remove(account);
            this.accounts.add(account);
        }
        this.save();
        return account;
    }

    public boolean removeAccount(Account account) {
        boolean removed = account != null && this.accounts.remove(account);
        if (removed) {
            this.save();
        }
        return removed;
    }

    /**
     * Applies the account to the running client. The session is swapped through
     * {@link MinecraftUserAccessor}; when a world is open the player is returned
     * to the main menu so the new session is used for the next connection.
     */
    public boolean login(Account account) {
        if (account == null) {
            return false;
        }
        if (account.getType() == AccountType.MICROSOFT && account.getAccessToken().isEmpty()) {
            return false;
        }
        Minecraft minecraft = Minecraft.getInstance();
        String token = account.getAccessToken().isEmpty() ? "0" : account.getAccessToken();
        User user = new User(account.getName(), account.getUuid(), token, java.util.Optional.<String>empty(), java.util.Optional.<String>empty());
        try {
            ((MinecraftUserAccessor)(Object)minecraft).night$setUser(user);
        }
        catch (Throwable throwable) {
            night.Night.LOGGER.error("Failed to apply session for {}", account.getName(), throwable);
            return false;
        }
        this.currentName = account.getName();
        this.addAccount(account);
        this.save();
        if (minecraft.level != null) {
            minecraft.disconnectFromWorld(Component.literal("[Night] Logged in as " + account.getName()));
        }
        return true;
    }

    public Account loginOffline(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        String trimmed = name.trim();
        if (trimmed.length() > 16) {
            trimmed = trimmed.substring(0, 16);
        }
        for (Account existing : this.accounts) {
            if (existing.getType() == AccountType.OFFLINE && existing.getName().equalsIgnoreCase(trimmed)) {
                return this.login(existing) ? existing : null;
            }
        }
        Account created = Account.offline(trimmed);
        this.accounts.add(created);
        this.save();
        return this.login(created) ? created : null;
    }

    private void load() {
        if (!Files.exists(this.storageFile)) {
            return;
        }
        try (Reader reader = Files.newBufferedReader(this.storageFile, StandardCharsets.UTF_8)) {
            JsonElement parsed = JsonParser.parseReader(reader);
            if (!parsed.isJsonArray()) {
                return;
            }
            JsonArray array = parsed.getAsJsonArray();
            for (JsonElement element : array) {
                if (!element.isJsonObject()) {
                    continue;
                }
                JsonObject object = element.getAsJsonObject();
                String name = object.has("name") ? object.get("name").getAsString() : "";
                String rawUuid = object.has("uuid") ? object.get("uuid").getAsString() : "";
                String typeName = object.has("type") ? object.get("type").getAsString() : AccountType.OFFLINE.name();
                AccountType type;
                try {
                    type = AccountType.valueOf(typeName);
                }
                catch (IllegalArgumentException ignored) {
                    type = AccountType.OFFLINE;
                }
                UUID uuid;
                try {
                    uuid = rawUuid.isEmpty() ? null : UUID.fromString(rawUuid);
                }
                catch (IllegalArgumentException ignored) {
                    uuid = null;
                }
                Account account = new Account(name, uuid, type, object.has("refreshToken") ? object.get("refreshToken").getAsString() : "", object.has("accessToken") ? object.get("accessToken").getAsString() : "");
                if (!this.accounts.contains(account)) {
                    this.accounts.add(account);
                }
                if (object.has("current") && object.get("current").getAsBoolean()) {
                    this.currentName = account.getName();
                }
            }
        }
        catch (IOException | RuntimeException exception) {
            night.Night.LOGGER.error("Failed to read accounts from {}", this.storageFile, exception);
        }
    }

    public void save() {
        try {
            Files.createDirectories(this.storageFile.getParent());
            JsonArray array = new JsonArray();
            for (Account account : this.accounts) {
                JsonObject object = new JsonObject();
                object.addProperty("name", account.getName());
                object.addProperty("uuid", account.getUuid().toString());
                object.addProperty("type", account.getType().name());
                object.addProperty("refreshToken", account.getRefreshToken());
                object.addProperty("accessToken", account.getAccessToken());
                object.addProperty("current", account.getName().equalsIgnoreCase(this.getCurrentName()));
                array.add(object);
            }
            try (Writer writer = Files.newBufferedWriter(this.storageFile, StandardCharsets.UTF_8)) {
                GSON.toJson(array, writer);
            }
        }
        catch (IOException | RuntimeException exception) {
            night.Night.LOGGER.error("Failed to save accounts to {}", this.storageFile, exception);
        }
    }
}
