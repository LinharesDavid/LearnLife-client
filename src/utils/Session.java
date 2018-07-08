package utils;

public class Session {
    private String token;
    private static Session instance;

    public static Session getInstance() {
        return  instance == null ? new Session() : instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
