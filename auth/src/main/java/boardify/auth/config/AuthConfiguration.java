package boardify.auth.config;

import boardify.commonsecurity.config.JwtAuthenticationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@Configuration
@ComponentScan(basePackages = {
        "boardify.commonsecurity.config",
        "boardify.commonsecurity.filters.microserviceFilters",
        "boardify.auth"},
        excludeFilters = @ComponentScan.Filter(type = ASSIGNABLE_TYPE,
                value = {
                        JwtAuthenticationConfig.class
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
