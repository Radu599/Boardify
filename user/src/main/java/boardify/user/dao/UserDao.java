package boardify.user.dao;

import boardify.user.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserDao {

    String findLocationByEmail(String email);

    User updateLocation(String email, String location);

    void updateAvatar(MultipartFile imageFile, String email, String avatarPath);

    void savePhotoImage(MultipartFile imageFile, String email, String path) throws IOException;

    User findUser(String username);

    void saveUser(User boardifyUser);
}
