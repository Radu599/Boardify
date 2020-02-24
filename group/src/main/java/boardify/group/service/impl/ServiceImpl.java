package boardify.group.service.impl;

import boardify.group.dao.GameGroupDao;
import boardify.group.dao.GroupMembersDao;
import boardify.group.dto.UserDto;
import boardify.group.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

//TODO:
@org.springframework.stereotype.Service
@Primary
@Component
public class ServiceImpl implements Service, UserDetailsService {

    //@Autowired
    //private GameGroupDao groupDao;
    @Autowired
    private GroupMembersDao groupMembersDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
        return new User("a@a.com", "a", authorities); //TODO:
    }

    @Override
    public List<UserDto> findGroupForUser(String email) {
        return groupMembersDao.findGroupForUser(email);
    }
}
