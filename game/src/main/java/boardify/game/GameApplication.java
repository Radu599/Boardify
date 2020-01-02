package boardify.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {
        "boardify.commonsecurity.config",
        "boardify.commonsecurity.filters.authMicroserviceFilters",
        "boardify.game"})
@EnableEurekaClient
public class GameApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameApplication.class, args);
    }
}
