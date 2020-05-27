package boardify.auth.config;

public class ApiConstants {
    private static final String HTTP = "http://";

    private static final String IP = "192.168.99.100";
    private static final String PORT = ":8084";
    // private static final String IP = "localhost";

    private static final String USER_BASE_API = HTTP + IP + PORT + "/users";
    public static final String FIND_USER = USER_BASE_API + "/";
}
