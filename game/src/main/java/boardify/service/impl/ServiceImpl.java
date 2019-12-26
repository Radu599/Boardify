package boardify.service.impl;

import boardify.dao.GameDao;
import boardify.model.Game;
import boardify.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceImpl implements Service {

    @Autowired
    private GameDao gameDao;

    @Override
    public List<Game> findAllGames() {

        return gameDao.findAllGames();
    }
}
