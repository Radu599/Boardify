package boardify.commonsecurity.filters.microserviceFilters;

import boardify.commonsecurity.config.JwtAuthenticationConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    public JwtTokenAuthenticationFilter(JwtAuthenticationConfig jwtAuthenticationConfig) {

        this.jwtAuthenticationConfig = jwtAuthenticationConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        String token = httpServletRequest.getHeader(jwtAuthenticationConfig.getHeader());

        if (token != null) {
            token = token.replace(jwtAuthenticationConfig.getPrefixHeader() + " ", "");
            try {
                Claims claims = extractAllClaims(token, jwtAuthenticationConfig.getSecretSignIn());
                String username = claims.getSubject();
                List<String> authorities = claims.get("authorities", List.class);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    setAuthenticatedUser(username, authorities);
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void setAuthenticatedUser(String username, List<String> authorities) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, null,
                convertStringAuthorityToSimpleGrantedAuthority(authorities)
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private Collection<SimpleGrantedAuthority> convertStringAuthorityToSimpleGrantedAuthority(List<String> authorities) {

        return authorities.stream()
                .filter(Objects::nonNull)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Claims extractAllClaims(String token, String secret) {

        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
