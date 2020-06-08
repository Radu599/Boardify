package boardify.user.service.impl;

import boardify.user.dao.UserDao;
import boardify.user.dto.RegisterResponse;
import boardify.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource("classpath:/test.properties")
//@ContextConfiguration("classpath:/spring-beans.xml")
@Rollback()
class ServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private ServiceImpl service;

    @Test
    void findLocationByEmail() {

        Mockito.when(userDao.findLocationByEmail("a@a.com")).thenReturn("France,Berlin");
        String location = service.findLocationByEmail("a@a.com");
        String expectedLocation = "France,Berlin";
        Assertions.assertEquals(expectedLocation, location);
    }

    @Test
    void updateLocation() {

        String expectedLocation = "Romania,Sibiu";
        Mockito.when(userDao.updateLocation("a@a.com", expectedLocation)).thenReturn(User.builder().username("a@a.com").location(expectedLocation).build());
        String actualLocation = service.updateLocation("a@a.com", expectedLocation);
        Assertions.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void findUser() {

        String email = "a@a.com";
        User expectedUser = User.builder().username(email).password("pass").build();
        Mockito.when(userDao.findUser(email)).thenReturn(expectedUser);
        User user = service.findUser(email);
        Assertions.assertEquals(expectedUser, user);
    }

    @Test
    void registerUser() {

        String email = "a@a.com";
        String password = "pass";
        // TODO:
        // Mockito.when(userDao.saveUser(User.builder().username(email).password(password).build()));
        RegisterResponse registerResponse = service.registerUser(email, password);
    }
}
