package boardify.auth.config;

import java.util.Arrays;

public class ApiConstants {

    private static final String development = "{development}";
    private static final String HTTP = "http://";
    private static final String localhost = "localhost";
    private static final String dockerIp = "192.168.99.100";
    private static final String PORT = ":8084";

    private static String USER_BASE_API_DEV = HTTP + localhost + PORT + "/users";
    private static String USER_BASE_API_PROD = HTTP + dockerIp + PORT + "/users";

    public static String getFindUser(String[] environment) {

        boolean contains = Arrays.asList(environment).contains(development);
        return contains ?USER_BASE_API_DEV:USER_BASE_API_PROD;
    }
}
