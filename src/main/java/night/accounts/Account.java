package night.accounts;

import java.util.Objects;
import java.util.UUID;

/**
 * An immutable description of a Minecraft account known to the alt manager.
 *
 * <p>Offline accounts only carry a username and the deterministic offline UUID;
 * Microsoft accounts additionally keep the Minecraft access token and the
 * Microsoft refresh token so the session can be restored without a new login.</p>
 */
public final class Account {
    private final String name;
    private final UUID uuid;
    private final AccountType type;
    private final String refreshToken;
    private final String accessToken;

    public Account(String name, UUID uuid, AccountType type, String refreshToken, String accessToken) {
        this.name = name == null ? "Unknown" : name;
        this.uuid = uuid == null ? UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.name).getBytes(java.nio.charset.StandardCharsets.UTF_8)) : uuid;
        this.type = type == null ? AccountType.OFFLINE : type;
        this.refreshToken = refreshToken == null ? "" : refreshToken;
        this.accessToken = accessToken == null ? "" : accessToken;
    }

    public static Account offline(String name) {
        UUID id = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(java.nio.charset.StandardCharsets.UTF_8));
        return new Account(name, id, AccountType.OFFLINE, "", "");
    }

    public String getName() {
        return this.name;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public AccountType getType() {
        return this.type;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public boolean hasTokens() {
        return !this.accessToken.isEmpty();
    }

    public Account withTokens(String refreshToken, String accessToken) {
        return new Account(this.name, this.uuid, this.type, refreshToken, accessToken);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Account)) {
            return false;
        }
        Account account = (Account)other;
        return this.type == account.type && this.uuid.equals(account.uuid) && this.name.equalsIgnoreCase(account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name.toLowerCase(), this.uuid, this.type);
    }

    @Override
    public String toString() {
        return this.name + " (" + this.type.getDisplayName() + ", " + this.uuid + ")";
    }
}
