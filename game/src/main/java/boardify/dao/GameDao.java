package boardify.dao;

import boardify.model.Game;

import java.util.List;

public interface GameDao {

    List<Game> findAllGames();

    //Game findById(int id);

    //int addEntity(Game game);

    //void deleteEntity(int id);

    //Game updateEntity(Game entity);
}
