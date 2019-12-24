package boardify.commonsecurity.filters.authMicroserviceFilters;

import boardify.commonsecurity.filters.authMicroserviceFilters.util.AuthJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * If user is not authenticated and the request is not for login we will find JWT from Authorization header and if the token is valid, we will authenticate the user
 */
@Component
public class AuthJwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService service;

    @Autowired
    private AuthJwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        if (skipIfLogin(httpServletRequest, httpServletResponse, filterChain))
            return;

        final String authorizationHeader = httpServletRequest.getHeader(jwtUtil.getConfig().getHeader());

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(jwtUtil.getConfig().getPrefixHeader() + " ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        loginUserAlreadyLoggedInAndValidToken(httpServletRequest, username, jwt);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void loginUserAlreadyLoggedInAndValidToken(@NonNull HttpServletRequest httpServletRequest, String username, String jwt) {

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = service.loadUserByUsername(username);
            loginUserIfValidToken(httpServletRequest, userDetails, jwt);
        }
    }

    private void loginUserIfValidToken(@NonNull HttpServletRequest httpServletRequest, UserDetails userDetails, String jwt) {

        if (jwtUtil.validateToken(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    private boolean skipIfLogin(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws IOException, ServletException {

        //if the request is for login purpose, then this filter will be omitted
        if (httpServletRequest.getRequestURI().equals(jwtUtil.getConfig().getLoginUrl())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return true;
        }
        return false;
    }
}