package boardify.user.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Service {

    String findLocationByEmail(String email);

    void updateLocation(String email, String location);

    void saveAvatar(MultipartFile imageFile, String email) throws IOException;
}
