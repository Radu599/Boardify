package boardify.auth.dao;

import boardify.auth.model.BoardifyUser;
public interface UserDao {

    BoardifyUser findUser(String username);

    void saveUser(BoardifyUser boardifyUser);
}
