package boardify.group.dao.jpaRepository;

import boardify.group.dao.GameGroupDao;
import boardify.group.model.GameGroup;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@EnableJpaRepositories(basePackageClasses = GameGroupJpaRepository.class)
public class GameGroupDaoJpa implements GameGroupDao {

    private GameGroupJpaRepository gameGroupJpaRepository;
    //private Logger logger = LogManager.getLogger(GameDaoJpa.class);

    public GameGroupDaoJpa(GameGroupJpaRepository jpaRepository) {
        this.gameGroupJpaRepository = jpaRepository;
    }

    @Override
    public List<GameGroup> findAllGameGroups() {

        return gameGroupJpaRepository.findAll()
                .stream()
                .map(this::convertGameGroupPersistenceToGameGroup)
                .collect(Collectors.toList());
    }

    @Override
    public int save(int gameID) {

        GameGroupPersistance gameGroupPersistance = gameGroupJpaRepository.save(this.convertGameGroupToGameGroupPersistance(new GameGroup(gameID)));
        return gameGroupPersistance.getId();
    }

    @Override
    public int findGameForGroup(int groupID) {
        Optional<GameGroupPersistance> gameGroupPersistanceOptional = gameGroupJpaRepository.findById(groupID);
        return gameGroupPersistanceOptional.get().getGameId();
    }

    private GameGroup convertGameGroupPersistenceToGameGroup(GameGroupPersistance gameGroupPersistance) {

        return gameGroupPersistance == null ? null : GameGroup
                .builder()
                .id(gameGroupPersistance.getId())
                .gameId(gameGroupPersistance.getGameId())
                .build();
    }

    private GameGroupPersistance convertGameGroupToGameGroupPersistance(GameGroup gameGroup) {

        return gameGroup == null ? null : GameGroupPersistance.builder()
                .gameId(gameGroup.getGameId())
                .id(gameGroup.getId())
                .build();
    }
}
