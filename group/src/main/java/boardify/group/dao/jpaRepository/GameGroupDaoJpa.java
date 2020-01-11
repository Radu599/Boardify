package boardify.group.dao.jpaRepository;

import boardify.group.dao.GameGroupDao;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@Component
@EnableJpaRepositories(basePackageClasses = GameGroupJpaRepository.class)
public class GameGroupDaoJpa implements GameGroupDao {

    private GameGroupJpaRepository gameGroupJpaRepository;

    //private Logger logger = LogManager.getLogger(GameDaoJpa.class);

    public GameGroupDaoJpa(GameGroupJpaRepository jpaRepository) {
        this.gameGroupJpaRepository = jpaRepository;
    }


}
