package boardify.user.dao.jpaRepository;

import boardify.user.dao.UserDao;
import boardify.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource("classpath:/test.properties")
//@ContextConfiguration("classpath:/spring-beans.xml")
@Rollback()
class UserDaoJpaTest {

    private UserDao userDao;

    @Autowired
    public UserDaoJpaTest(UserDao userDao){
        this.userDao = userDao;
    }

    @Test
    void findLocationByEmail() {

        String actualLocation = userDao.findLocationByEmail("a@a.com");
        String expectedLocation = "France,Paris";
        Assertions.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void updateLocation() {

        String email = "a@a.com";
        String newLocation = "Romania,Cluj";
        userDao.updateLocation(email, newLocation);
        User userResult = userDao.findUser(email);
        Assertions.assertEquals(userResult.getLocation(), newLocation);
    }

    @Test
    void findUser() {

        User user = userDao.findUser("a@a.com");
        User expectedUser = new User("a@a.com", "a", "France,Paris", null, null);
        Assertions.assertEquals(expectedUser, user);
    }

    @Test
    void register() {

        String email = "new@new.new";
        User expectedUser = User.builder().username(email).password("").location("location").build();
        userDao.saveUser(expectedUser);
        User actualUser = userDao.findUser(email);
        Assertions.assertEquals(expectedUser, actualUser);
    }
}
