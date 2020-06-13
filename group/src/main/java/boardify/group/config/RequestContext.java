package boardify.group.config;

import org.springframework.stereotype.Component;

/*TODO:
    Request Context, Request Filter and RestTemplateInterceptor are used in order to pass jwt token from current microservice
    to an other microservice.
     Add getPeticionFilter() @Bean in SecurityConfig of all microservices*/
@Component
public class RequestContext {

    public static final String REQUEST_HEADER_NAME = "Authorization";

    private static final ThreadLocal<RequestContext> CONTEXT = new ThreadLocal<>();

    private String token;

    public static RequestContext getContext() {

        RequestContext result = CONTEXT.get();

        if (result == null) {
            result = new RequestContext();
            CONTEXT.set(result);
        }

        return result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
