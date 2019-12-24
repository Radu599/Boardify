package boardify.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {
        "boardify.commonsecurity.config",
        "boardify.commonsecurity.filters.authMicroserviceFilters",
        "boardify.auth"})
@EnableEurekaClient
public class AuthApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthApplication.class, args);
    }
}
