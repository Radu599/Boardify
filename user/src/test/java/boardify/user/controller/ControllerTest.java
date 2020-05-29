package boardify.user.controller;

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
class ControllerTest {


}
