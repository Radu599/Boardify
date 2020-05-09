package boardify.user.dao.jpaRepository;

import boardify.user.dao.UserDao;
import boardify.user.model.User;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@Component
@EnableJpaRepositories(basePackageClasses = UserJpaRepository.class)
public class UserDaoJpa implements UserDao {

    private UserJpaRepository jpaRepository;

    //private Logger logger = LogManager.getLogger(GameDaoJpa.class);

    public UserDaoJpa(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public String findLocationByEmail(String email) {

        return convertUserPersistenceToUser(jpaRepository.findById(email).get()).getLocation();
    }

    @Override
    public User updateLocation(String email, String location) {

        UserPersistance oldUser = jpaRepository.findById(email).orElse(null);
        if (oldUser == null) {
            return null;
        }
        oldUser = UserPersistance.builder()
                .email(oldUser.getEmail())
                .location(oldUser.getLocation())
                .password(oldUser.getPassword())//TODO:
                .build();
        UserPersistance user = UserPersistance.builder()
                .email(email)
                .location(location)
                .password(oldUser.getPassword())
                .build();
        jpaRepository.save(user);
        return convertUserPersistenceToUser(oldUser);
    }

    private User convertUserPersistenceToUser(UserPersistance userPersistence) {

        return userPersistence == null ? null : User
                .builder()
                .email(userPersistence.getEmail())
                .location(userPersistence.getLocation())
                .password(userPersistence.getPassword()) //TODO:
                .build();
    }

    private UserPersistance convertUserToUserPersistence(User user) {

        return user == null ? null : UserPersistance
                .builder()
                .email(user.getEmail())
                .password(user.getPassword())//TODO:???
                .location(user.getLocation())
                .build();
    }
}
