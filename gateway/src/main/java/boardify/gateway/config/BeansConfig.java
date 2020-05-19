package boardify.gateway.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class BeansConfig {

    @Bean
    public ZuulFilter postFilter() {
        return new ZuulFilter() {

            @Override
            public boolean shouldFilter() {
                return true;
            }

            @Override
            public Object run() throws ZuulException {
                System.out.println("Pre Filter - run");
                HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
                System.out.println("Request Method : "+request.getMethod());
                System.out.println("Request URL : "+request.getRequestURL().toString());
                return null;
            }

            @Override
            public String filterType() {
                return "pre";
            }

            @Override
            public int filterOrder() {
                return 1;
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(CorsConfiguration.ALL);
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
