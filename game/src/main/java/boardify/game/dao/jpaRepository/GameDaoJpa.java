package boardify.game.dao.jpaRepository;

import boardify.game.dao.GameDao;
import boardify.game.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@EnableJpaRepositories(basePackageClasses = GameJpaRepository.class)
public class GameDaoJpa implements GameDao {

    private GameJpaRepository gameJpaRepository;

    @Autowired
    public GameDaoJpa(GameJpaRepository jpaRepository) {
        this.gameJpaRepository = jpaRepository;
    }

    @Override
    public List<Game> findAllGames() {

        return gameJpaRepository.findAll()
                .stream()
                .map(this::parseToGame)
                .collect(Collectors.toList());
    }

    @Override
    public int getMinimumNumberOfPlayers(int gameId) {

         Optional<GamePersistence> gamePersistence = gameJpaRepository.findById(gameId);
         return gamePersistence.get().getMinimumNumberOfPlayers();
    }

    private Game parseToGame(GamePersistence gamePersistence) {

        return gamePersistence == null ? null : Game
                .builder()
                .id(gamePersistence.getId())
                .name(gamePersistence.getName())
                .averagePlayingTime(gamePersistence.getAveragePlayingTime())
                .description(gamePersistence.getDescription())
                .maximumNumberOfPlayers(gamePersistence.getMaximumNumberOfPlayers())
                .minimumNumberOfPlayers(gamePersistence.getMinimumNumberOfPlayers())
                .suggestedAge(gamePersistence.getSuggestedAge())
                .imageLink(gamePersistence.getImageLink())
                .build();
    }
}
