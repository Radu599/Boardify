package boardify.user.controller;

import boardify.user.service.Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MockitoSettings(strictness = Strictness.LENIENT)
@Rollback()
@Transactional
class ControllerTest {

    @Mock
    private Service service;

    @Spy
    @InjectMocks
    private Controller controller = new Controller();

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    //https://stackoverflow.com/questions/45825955/using-spring-mockmvc-to-test-optional-path-variables
    @Test
    void findLocationByEmail() throws Exception {

        String location = "Romania,Sibiu";
        Mockito.when(service.findLocationByEmail("a@a.com")).thenReturn(location);
        MvcResult mvcResult = mockMvc.perform(get("/users/location/a@a.com").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        Assertions.assertEquals("", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void updateLocation() {
    }

    @Test
    void findUser() {
    }

    @Test
    void register() {
    }
}
