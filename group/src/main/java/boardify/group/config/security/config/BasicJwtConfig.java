package boardify.group.config.security.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component
@Primary
public class BasicJwtConfig {

    @Value("${security.jwt.header}")
    private String header;

    @Value("${security.jwt.header.prefix}")
    private String prefixHeader;

    @Value("${security.jwt.expiration}")
    private long expiration;

    @Value("${security.jwt.secret}")
    private String secretSignIn;
}
