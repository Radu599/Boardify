package boardify.user.dao.jpaRepository;

import boardify.user.dao.UserDao;
import boardify.user.model.User;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        return convertUserPersistanceToUser(jpaRepository.findById(email).get()).getLocation();
    }

    @Override
    public User updateLocation(String email, String location) {

        UserPersistance oldUser = jpaRepository.findById(email).orElse(null);
        if (oldUser == null) {
            return null;
        }

        //deep copy for oldUser because JpaRepository will modify oldUser after save
        //(JpaRepository will bind the entity returned by 'findById' method and all changes from DB for the given row in the table will change the entity fields)
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
        return convertUserPersistanceToUser(oldUser);
    }

    private User convertUserPersistanceToUser(UserPersistance userPersistence) {

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
