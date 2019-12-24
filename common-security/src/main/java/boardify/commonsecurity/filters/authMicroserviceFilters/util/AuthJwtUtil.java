package boardify.commonsecurity.filters.authMicroserviceFilters.util;

import boardify.commonsecurity.config.JwtAuthenticationConfig;
import boardify.commonsecurity.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AuthJwtUtil extends JwtUtil {

    private final JwtAuthenticationConfig config;

    @Autowired
    public AuthJwtUtil(JwtAuthenticationConfig config) {

        this.config = config;
    }

    public JwtAuthenticationConfig getConfig(){

        return config;
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        List<String> generate = generateAuthoritiesList(userDetails);
        claims.put("authorities",generate);
        return createToken(claims, userDetails.getUsername());
    }

    public boolean validateToken(String token, UserDetails userDetails) {

        final String username = extractUsername(token);
        return !isTokenExpired(token) && username != null && username.equals(userDetails.getUsername());
    }

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token,config.getSecretSignIn());
        return claimsResolver.apply(claims);
    }

    private String createToken(Map<String, Object> claims, String username) {

        final long currentTimestamp = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(currentTimestamp))
                .setExpiration(new Date(currentTimestamp + config.getExpiration()))
                .signWith(SignatureAlgorithm.HS256, config.getSecretSignIn())
                .compact();
    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    private List<String> generateAuthoritiesList(UserDetails userDetails) {

        return userDetails
                .getAuthorities()
                .stream()
                .filter(Objects::nonNull)
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
