package boardify.dao.jpaRepository;

import boardify.dao.GameDao;
import boardify.model.Game;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableJpaRepositories(basePackageClasses = EntityJpaRepository.class)
public class GameDaoJpa implements GameDao {

    private EntityJpaRepository jpaRepository;

    //private Logger logger = LogManager.getLogger(GameDaoJpa.class);

    public GameDaoJpa(EntityJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Game> findAllGames() {

        return jpaRepository.findAll()
                .stream()
                .map(this::convertGamePersistenceToGame)
                .collect(Collectors.toList());
    }


    private Game convertGamePersistenceToGame(GamePersistence entityPersistence) {

        return entityPersistence == null ? null : Game
                .builder()
                .id(entityPersistence.getId())
                .name(entityPersistence.getName())
                .build();
    }
}
