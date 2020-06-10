package boardify.game.config;

import boardify.commonsecurity.config.JwtAuthenticationConfig;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@Configuration
@ComponentScan(basePackages = {
        "boardify.commonsecurity.config",
        "boardify.commonsecurity.filters.microserviceFilters",
        "boardify.commonsecurity.util",
        "boardify.game"},
        excludeFilters = @ComponentScan.Filter(type = ASSIGNABLE_TYPE,
                value = {
                        JwtAuthenticationConfig.class
                })
)
public class GameConfiguration {

    @Bean
    @LoadBalanced
    public WebClient.Builder buildWebClientBuilder() {

        return WebClient.builder();
    }
}
