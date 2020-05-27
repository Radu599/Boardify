package boardify.user.service;

import boardify.user.dto.RegisterResponse;
import boardify.user.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Service {

    String findLocationByEmail(String email);

    void updateLocation(String email, String location);

    void saveAvatar(MultipartFile imageFile, String email) throws IOException;

    User findUser(String email);

    RegisterResponse registerUser(String username, String password);
}
