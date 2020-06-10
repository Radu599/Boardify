package boardify.auth.service.impl;

import boardify.auth.dao.UserDao;
import boardify.auth.dto.AuthenticationResponse;
import boardify.auth.model.BoardifyUser;
import boardify.auth.service.Service;
import boardify.auth.service.exception.LoginExceptionType;
import boardify.auth.service.exception.LoginServiceException;
import boardify.commonsecurity.filters.authMicroserviceFilters.util.AuthJwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@org.springframework.stereotype.Service
@Primary
@Component
public class ServiceImpl implements Service, UserDetailsService {

    private final Logger logger = LogManager.getLogger(ServiceImpl.class);
    private final UserDao userDao;
    private AuthenticationManager authenticationManager;
    private final AuthJwtUtil authJwtUtil;

    public ServiceImpl(UserDao userDao, AuthJwtUtil authJwtUtil) {
        this.userDao = userDao;
        this.authJwtUtil = authJwtUtil;
    }
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse login(String username, String password) {

        logger.info("LOG START - login");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            final String jwt = authJwtUtil.generateToken(userDetails);
            logger.info("LOG FINISH - login");
            return new AuthenticationResponse(jwt);
        } catch (BadCredentialsException e) {
            logger.error("Login failed " + e.getMessage());
            throw new LoginServiceException("Invalid credentials", LoginExceptionType.INVALID_CREDENTIALS, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("LOG START - loadUserByUsername");
        BoardifyUser user = userDao.findUser(username);
        if (user == null)
            throw new UsernameNotFoundException("Username not found " + username);
        user.setRole("NORMAL"); //TODO: user microservice
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
        logger.info("LOG FINISH - loadUserByUsername");
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
