package boardify.game.service.impl;

import boardify.game.dao.GameDao;
import boardify.game.model.Game;
import boardify.game.service.Service;
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
public class ServiceImpl implements Service {

    @Autowired
    private GameDao gameDao;

    @Override
    public List<Game> findAllGames() {

        return gameDao.findAllGames();
    }
}
