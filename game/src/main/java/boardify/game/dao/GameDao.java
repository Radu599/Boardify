package boardify.game.dao;

import boardify.game.model.Game;

import java.util.List;

public interface GameDao {

    List<Game> findAllGames();
    int getMinimumNumberOfPlayers(int gameId);
}
