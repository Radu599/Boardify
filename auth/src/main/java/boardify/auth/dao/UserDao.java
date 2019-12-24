package boardify.auth.dao;

import boardify.auth.model.MyUser;

public interface UserDao {

    MyUser findUser(String username);
}
