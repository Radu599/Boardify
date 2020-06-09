package boardify.commonsecurity.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component
public class JwtAuthenticationConfig{

    @Value("${security.jwt.header}")
    private String header;

    @Value("${security.jwt.header.prefix}")
    private String prefixHeader;

    @Value("${security.jwt.expiration}")
    private long expiration;

    @Value("${security.jwt.secret}")
    private String secretSignIn;

    @Value("${security.jwt.url}")
    private String loginUrl;
}
