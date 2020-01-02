package boardify.user.service.impl;

import boardify.commonsecurity.filters.authMicroserviceFilters.util.AuthJwtUtil;
import boardify.user.dao.UserDao;
import boardify.user.service.Service;
import boardify.user.service.exception.UserServiceException;
import boardify.user.service.exception.UserExceptionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
        return new User("a@a.com", "a", authorities);//TODO:
    }

    @Override
    public String findLocationByEmail(String email) {
        return userDao.findLocationByEmail(email);
    }

    @Override
    public void updateLocation(String email, String location) {

        if (email == null) {
            throw new UserServiceException("Given user has null id!", UserExceptionType.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        if (userDao.updateLocation(email, location) == null) {
            throw new UserServiceException("Doesn't exist a user with id = " + email, UserExceptionType.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
}
