package boardify.user.service.impl;

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
}
