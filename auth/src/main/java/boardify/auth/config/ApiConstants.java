package boardify.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class ApiConstants {

    @Autowired
    private static Environment environment;
    private static String development = "development";

    private static final String HTTP = "http://";

    private static final String localhost = "localhost";
    private static final String dockerIp = "192.168.99.100";
    private static final String PORT = ":8084";

    private static final String USER_BASE_API = HTTP + findIp() + PORT + "/users";

    public static String findIp(){
        return  (environment.getActiveProfiles().equals(development))? localhost :(dockerIp);
    }

    public static final String FIND_USER = USER_BASE_API + "/";
}
