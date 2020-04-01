package boardify.game.service.impl;

import boardify.game.dao.GameDao;
import boardify.game.model.Game;
import boardify.game.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

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

    @Override
    public int getMinimumNumberOfPlayers(int gameId) {

        return gameDao.getMinimumNumberOfPlayers(gameId);
    }
}
