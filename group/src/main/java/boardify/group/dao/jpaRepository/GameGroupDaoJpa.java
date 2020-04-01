package boardify.group.dao.jpaRepository;

import boardify.group.dao.GameGroupDao;
import boardify.group.model.GameGroup;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableJpaRepositories(basePackageClasses = GameGroupJpaRepository.class)
public class GameGroupDaoJpa implements GameGroupDao {

    private GameGroupJpaRepository gameGroupJpaRepository;
    private GroupMembersDaoJpa groupMembersDaoJpa;

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

    private GameGroup convertGameGroupPersistenceToGameGroup(GameGroupPersistance gameGroupPersistance) {

        return gameGroupPersistance == null ? null : GameGroup
                .builder()
                .id(gameGroupPersistance.getId())
                .gameId(gameGroupPersistance.getGameId())
                .build();
    }
}
