package boardify.user.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * class for @Beans and other configurations
 */
@Configuration
public class UserConfiguration {

    @Bean
    @LoadBalanced
    public WebClient.Builder getWebClientBuilder() {

        return WebClient.builder();
    }


    // if you want to use RestTemplate for calls between microservices using EurekaServiceDiscovery, you must add here '@LoadBalanced' annotation
    // for more information about '@LoadBalanced' check here:
    // ** https://www.logicbig.com/tutorials/spring-framework/spring-cloud/rest-template-load-balancer.html
    // ** or ask me
    @Bean
    public RestTemplate getRestTemplate() {

        return new RestTemplate();
    }
}
