package boardify.user.service.impl;

import boardify.user.config.FileConstants;
import boardify.user.dao.UserDao;
import boardify.user.service.Service;
import boardify.user.service.exception.UserExceptionType;
import boardify.user.service.exception.UserServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@org.springframework.stereotype.Service
@Primary
@Component
public class ServiceImpl implements Service {

    private final Logger logger = LogManager.getLogger(ServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public String findLocationByEmail(String email) {
        return userDao.findLocationByEmail(email);
    }

    @Override
    public void updateLocation(String email, String location) {

        if (email == null) {
            throw new UserServiceException("Given user has null id!", UserExceptionType.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        if (userDao.updateLocation(email, location) == null) {
            throw new UserServiceException("Doesn't exist a user with id = " + email, UserExceptionType.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        assert(userDao.findLocationByEmail(email).equals(location));
    }

    @Override
    public void saveAvatar(MultipartFile imageFile, String email) throws IOException {

        // this gets us to src/main/resources
        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();
        String finalPath = absolutePath + FileConstants.AVATAR_SAVING_RELATIVE_DIRECTORY;
        userDao.savePhotoImage(imageFile, email, finalPath);
        userDao.updateAvatar(imageFile, email, finalPath);
    }
}
