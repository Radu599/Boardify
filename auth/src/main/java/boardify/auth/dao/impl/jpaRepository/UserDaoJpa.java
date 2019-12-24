package boardify.auth.dao.impl.jpaRepository;

import boardify.auth.dao.UserDao;
import boardify.auth.model.MyUser;
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
    public MyUser findUser(String username) {

        logger.info("+++++++++LOGGING findUser+++++++++");
        logger.info("username: {}", username);
        try {
            UserPersistence userPersistence = userJpaRepository.findById(username).orElse(null);
            logger.info("+++++++++SUCCESSFUL LOGGING findUser+++++++++");
            return convertUserPersistenceToMyUser(userPersistence);
        }
        catch (Exception e){
            logger.error("ERROR IN FIND USER BY USERNAME:{}",username);
            logger.error("MESSAGE: {}",e.getMessage());
        }
        return null;
    }

    private MyUser convertUserPersistenceToMyUser(UserPersistence userPersistence) {

        return userPersistence == null ? null : MyUser.builder()
                .username(userPersistence.getUsername())
                .password(userPersistence.getPassword())
                .role(userPersistence.getRole().getType())
                .build();
    }
}
