package boardify.group.config;

import boardify.commonsecurity.config.JwtAuthenticationConfig;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@Configuration
@ComponentScan(basePackages = {
        "boardify.commonsecurity.config",
        "boardify.commonsecurity.filters.microserviceFilters",
        "boardify.commonsecurity.util",
        "boardify.group"},
        excludeFilters = @ComponentScan.Filter(type = ASSIGNABLE_TYPE,
                value = {
                        JwtAuthenticationConfig.class
                })
)
public class GroupConfiguration {

    @Bean
    @LoadBalanced
    public WebClient.Builder buildWebClientBuilder() {

        return WebClient.builder();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        System.out.println("INTERCEPTOR ADDED!!!!!!!!!!!!!!!!!!!!");

        RestTemplate restTemplate = new RestTemplate();
        this.addInterceptors(restTemplate);

        return restTemplate;
    }

    public void addInterceptors(RestTemplate restTemplate) {
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(new RestTemplateInterceptor());
        restTemplate.setInterceptors(interceptors);
    }
}
