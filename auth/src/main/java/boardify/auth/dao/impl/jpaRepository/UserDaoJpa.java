package boardify.auth.dao.impl.jpaRepository;

import boardify.auth.dao.UserDao;
import boardify.auth.model.BoardifyUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@Component
@EnableJpaRepositories(basePackageClasses = UserJpaRepository.class)
public class UserDaoJpa implements UserDao {

    private final Logger logger = LogManager.getLogger(UserDaoJpa.class);

    private UserJpaRepository userJpaRepository;

    public UserDaoJpa(UserJpaRepository userJpaRepository) {

        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public BoardifyUser findUser(String username) {

        logger.info("+++++++++LOGGING findUser+++++++++");
        logger.info("username: {}", username);
        try {
            BoardifyUserPersistance userPersistence = userJpaRepository.findById(username).orElse(null);
            logger.info("+++++++++SUCCESSFUL LOGGING findUser+++++++++");
            return convertBoardifyUserPersistenceToBoardifyUser(userPersistence);
        }
        catch (Exception e){
            logger.error("ERROR IN FIND USER BY USERNAME:{}",username);
            logger.error("MESSAGE: {}",e.getMessage());
        }
        return null;
    }

    @Override
    public void saveUser(BoardifyUser boardifyUser) {

        logger.info("+++++++++LOGGING saveUser+++++++++");
        try {
            BoardifyUserPersistance userPersistence = convertBoardifyUserToBoardifyUserPersistance(boardifyUser);
            RolePersistence rolePersistence = new RolePersistence();
            rolePersistence.setType("normal");
            rolePersistence.setId(2);
            userPersistence.setRole(rolePersistence);//TODO
            userJpaRepository.save(userPersistence);
            logger.info("+++++++++SUCCESSFUL LOGGING findUser+++++++++");
        }
        catch (Exception e){
            logger.error("ERROR IN FIND USER BY USERNAME:{}",boardifyUser.getUsername());
            logger.error("MESSAGE: {}",e.getMessage());
        }
    }

    private BoardifyUser convertBoardifyUserPersistenceToBoardifyUser(BoardifyUserPersistance boardifyUserPersistance) {

        return boardifyUserPersistance == null ? null : BoardifyUser.builder()
                .username(boardifyUserPersistance.getUsername())
                .password(boardifyUserPersistance.getPassword())
                .role(boardifyUserPersistance.getRole().getType())
                .build();
    }

    private BoardifyUserPersistance convertBoardifyUserToBoardifyUserPersistance(BoardifyUser boardifyUser) {

        return boardifyUser == null ? null : BoardifyUserPersistance.builder()
                .username(boardifyUser.getUsername())
                .password(boardifyUser.getPassword())
                //TODO: role
                .build();
    }
}