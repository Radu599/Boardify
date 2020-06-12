package boardify.group.config.security.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component
public class JwtAuthenticationConfig extends BasicJwtConfig {

    @Value("${security.jwt.url}")
    private String loginUrl;
}
