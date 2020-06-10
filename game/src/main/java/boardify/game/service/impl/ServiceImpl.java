package boardify.game.service.impl;

import boardify.game.dao.GameDao;
import boardify.game.model.Game;
import boardify.game.service.Service;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
//TODO:
@org.springframework.stereotype.Service
@Primary
@Component
public class ServiceImpl implements Service {

    private final GameDao gameDao;

    public ServiceImpl(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Override
    public List<Game> findAllGames() {

        return gameDao.findAllGames();
    }

    @Override
    public int getMinimumNumberOfPlayers(int gameId) {

        return gameDao.getMinimumNumberOfPlayers(gameId);
    }
}
