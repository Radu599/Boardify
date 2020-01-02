package boardify.user.dao;

import boardify.user.model.User;

import java.util.List;

public interface UserDao {

    String findLocationByEmail(String email);

    User updateLocation(String email, String location);
}
