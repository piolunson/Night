package night.accounts;

/**
 * The kind of session an {@link Account} represents.
 */
public enum AccountType {
    MICROSOFT("Microsoft"),
    OFFLINE("Offline"),
    SESSION("Session");

    private final String displayName;

    AccountType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
