public enum Credentials {
    PTC("username", "password"),
    GOOGLE("username", "password");

    public final String username;
    public final String password;

    Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
