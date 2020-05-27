package boardify.auth.dao.impl;

import boardify.auth.dao.UserDao;
import boardify.auth.model.BoardifyUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserDaoJpa implements UserDao {

    private final Logger logger = LogManager.getLogger(UserDaoJpa.class);

    @Autowired
    private RestTemplate restTemplate;

    public UserDaoJpa( ) {

    }

    @Override
    public BoardifyUser findUser(String username) {

        logger.info("++++LOGGING findUser Dao");
        BoardifyUser boardifyUser = restTemplate.getForObject("http://localhost:8084/users/" + username, BoardifyUser.class);
        logger.info("User is:");
        logger.info(boardifyUser.toString());
        logger.info("++++SUCCESSFULLY LOGGING findUser Dao");
        return boardifyUser;
    }
}
