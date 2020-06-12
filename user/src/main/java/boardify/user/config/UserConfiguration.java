package boardify.user.config;

import boardify.user.config.security.config.JwtAuthenticationConfig;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@Configuration
@ComponentScan(basePackages = {
        "boardify.user.config",
        "boardify.user.config.security.filters.microserviceFilters",
        "boardify.user"},
        excludeFilters = @ComponentScan.Filter(type = ASSIGNABLE_TYPE,
                value = {
                        JwtAuthenticationConfig.class
                })
)
public class UserConfiguration {

    @Bean
    @LoadBalanced
    public WebClient.Builder buildWebClientBuilder() {

        return WebClient.builder();
    }
}
