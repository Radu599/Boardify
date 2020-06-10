package boardify.auth.dao.impl;

import boardify.auth.dao.UserDao;
import boardify.auth.model.BoardifyUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static boardify.auth.config.ApiConstants.getFindUser;

@Component
public class UserDaoJpa implements UserDao {

    private final Logger logger = LogManager.getLogger(UserDaoJpa.class);
    private Environment environment;
    private RestTemplate restTemplate;

    @Autowired
    public UserDaoJpa(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    @Override
    public BoardifyUser findUser(String username) {

        logger.info("LOG START - findUser");
        logger.info("Request user from " + getFindUser(environment.getActiveProfiles()));
        BoardifyUser boardifyUser = restTemplate.getForObject(getFindUser(environment.getActiveProfiles()) + "/" + username, BoardifyUser.class);
        logger.info("User is:" + boardifyUser.toString());
        logger.info("LOG FINISH - findUser ");
        return boardifyUser;
    }
}
