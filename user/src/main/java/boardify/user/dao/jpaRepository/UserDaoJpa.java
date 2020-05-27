package boardify.user.dao.jpaRepository;

import boardify.user.dao.UserDao;
import boardify.user.model.User;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @Override
    public void updateAvatar(MultipartFile imageFile, String email, String avatarPath) {

        UserPersistance oldUser = jpaRepository.findById(email).orElse(null);
        if (oldUser == null) {
            return;
        }
        oldUser = UserPersistance.builder()
                .email(oldUser.getEmail())
                .location(oldUser.getLocation())
                .password(oldUser.getPassword())
                .avatarPath(oldUser.getAvatarPath())
                .build();
        UserPersistance user = UserPersistance.builder()
                .email(email)
                .location(oldUser.getLocation())
                .password(oldUser.getPassword())
                .avatarPath(avatarPath)
                .build();
        jpaRepository.save(user);
    }

    @Override
    public void savePhotoImage(MultipartFile imageFile, String name, String picPath) throws IOException {

        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(picPath + name + ".jpg");
        Files.write(path, bytes);
    }

    private User convertUserPersistenceToUser(UserPersistance userPersistence) {

        return userPersistence == null ? null : User
                .builder()
                .username(userPersistence.getEmail())
                .location(userPersistence.getLocation())
                .password(userPersistence.getPassword()) //TODO:
                .build();
    }

    private UserPersistance convertUserToUserPersistence(User user) {

        return user == null ? null : UserPersistance
                .builder()
                .email(user.getUsername())
                .password(user.getPassword())//TODO:???
                .location(user.getLocation())
                .build();
    }

    @Override
    public User findUser(String username) {

       /* logger.info("+++++++++LOGGING findUser+++++++++");
        logger.info("username: {}", username);*/
        try {
            UserPersistance userPersistence = jpaRepository.findById(username).orElse(null);
            //logger.info("+++++++++SUCCESSFUL LOGGING findUser+++++++++");
            return convertUserPersistenceToUser(userPersistence);
        }
        catch (Exception e){
            /*logger.error("ERROR IN FIND USER BY USERNAME:{}",username);
            logger.error("MESSAGE: {}",e.getMessage());*/
        }
        return null;
    }

    @Override
    public void saveUser(User boardifyUser) {

//        logger.info("+++++++++LOGGING saveUser+++++++++");
        try {
            UserPersistance userPersistence = convertUserToUserPersistence(boardifyUser);
            // TODO:
            //RolePersistence rolePersistence = new RolePersistence();
            //rolePersistence.setType("normal");
            //rolePersistence.setId(2);
           // userPersistence.setRole(rolePersistence);//TODO
            jpaRepository.save(userPersistence);
//            logger.info("+++++++++SUCCESSFUL LOGGING findUser+++++++++");
        } catch (Exception e) {
//            logger.error("ERROR IN FIND USER BY USERNAME:{}", boardifyUser.getUsername());
//            logger.error("MESSAGE: {}", e.getMessage());
        }
    }
}
