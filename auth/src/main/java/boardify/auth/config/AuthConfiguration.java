package boardify.auth.config;

import boardify.auth.security.config.JwtConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@Configuration
@ComponentScan(basePackages = {
        "boardify.auth.config",
        "boardify.auth.config.security.filters.microserviceFilters",
        "boardify.auth"},
        excludeFilters = @ComponentScan.Filter(type = ASSIGNABLE_TYPE,
                value = {
                        JwtConfig.class
                })
)
public class AuthConfiguration {

    @Bean
    public RestTemplate getRestTemplate() {

        System.out.println("INTERCEPTOR ADDED!!!!!!!!!!!!!!!!!!!!");

        RestTemplate restTemplate = new RestTemplate();
        // TODO: add the interceptor
        //this.addInterceptors(restTemplate);

        return restTemplate;
    }
}
