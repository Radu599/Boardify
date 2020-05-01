package boardify.game.dao.jpaRepository;

import boardify.game.dao.GameDao;
import boardify.game.model.Game;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@EnableJpaRepositories(basePackageClasses = GameJpaRepository.class)
public class GameDaoJpa implements GameDao {

    private GameJpaRepository jpaRepository;

    //private Logger logger = LogManager.getLogger(GameDaoJpa.class);

    public GameDaoJpa(GameJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Game> findAllGames() {

        return jpaRepository.findAll()
                .stream()
                .map(this::convertGamePersistenceToGame)
                .collect(Collectors.toList());
    }

    @Override
    public int getMinimumNumberOfPlayers(int gameId) {

         Optional<GamePersistence> gamePersistence = jpaRepository.findById(gameId);
         return gamePersistence.get().getMinimumNumberOfPlayers();
    }


    private Game convertGamePersistenceToGame(GamePersistence gamePersistence) {

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
