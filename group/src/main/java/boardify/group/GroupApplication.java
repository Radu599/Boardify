package boardify.group;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {
        "boardify.commonsecurity.config",
        "boardify.commonsecurity.filters.authMicroserviceFilters",
        "boardify.group"})
@EnableEurekaClient
public class GroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupApplication.class, args);
    }

}
