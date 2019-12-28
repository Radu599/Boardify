package boardify.user.service.impl;

import boardify.commonsecurity.filters.authMicroserviceFilters.util.AuthJwtUtil;
import boardify.user.dao.UserDao;
import boardify.user.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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
        return new User("a@a.com", "a", authorities);
    }

    @Override
    public String findLocationByEmail(String email) {
        return userDao.findLocationByEmail(email);
    }
}
