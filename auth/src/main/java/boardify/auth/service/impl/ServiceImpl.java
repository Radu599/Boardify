package boardify.auth.service.impl;

import boardify.auth.dao.UserDao;
import boardify.auth.dto.LoginResponse;
import boardify.auth.dto.RegisterResponse;
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
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthJwtUtil jwtUtil;

    @Override
    public LoginResponse login(String username, String password) {

        logger.info("+++++++++++++++++++++++++++++++LOGGING login+++++++++++++++++++++++++++++++");
        loggingCredentials(username, password);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            final String jwt = jwtUtil.generateToken(userDetails);
            final String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(null);
            logger.info("+++++++++++++++++++++++++++++++SUCCESSFUL LOGGING login+++++++++++++++++++++++++++++++");
            return new LoginResponse(jwt);
        } catch (BadCredentialsException e) {
            logger.error("ERROR IN LOGIN: {}",e.getMessage());
            throw new LoginServiceException("Incorrect username or password", LoginExceptionType.INVALID_CREDENTIALS, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public RegisterResponse registerUser(String username, String password) {
        logger.info("+++++++++++++++++++++++++++++++LOGGING register+++++++++++++++++++++++++++++++");
        loggingCredentials(username, password);

        //TODO:
        /*
        if (userDao.existsByUserName(username)) {
            throw new RegisterServiceException("Email address is already taken!", RegisterExceptionType.USERNAME_EXISTS, HttpStatus.BAD_REQUEST);
        }*/

        BoardifyUser boardifyUser = BoardifyUser.builder()
                .username(username)
                .password(password)
                .role("normal")
                .build();

        userDao.saveUser(boardifyUser);

        logger.info("+++++++++++++++++++++++++++++++SUCCESSFUL LOGGING register+++++++++++++++++++++++++++++++");
        //TODO:
        return new RegisterResponse(boardifyUser.getUsername());
    }

    private void loggingCredentials(String username, String password) {

        logger.info("username: {}",username);
        logger.info("password: {}",password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("+++++++++++++++++++++++++++++++LOGGING loadUserByUsername+++++++++++++++++++++++++++++++");
        BoardifyUser user = userDao.findUser(username);
        if (user == null)
            throw new UsernameNotFoundException("Doesn't exist an user with username " + username);
        user.setRole("NORMAL");//TODO: user microservice
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
        logger.info("+++++++++++++++++++++++++++++++SUCCESSFUL LOGGING loadUserByUsername+++++++++++++++++++++++++++++++");
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
