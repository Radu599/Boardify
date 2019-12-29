package boardify.user.controller;

import boardify.user.service.Service;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class Controller {

    private final Logger logger = LogManager.getLogger(Controller.class);

    @Autowired
    private Service service;


    @ApiOperation(value = "Find locations by email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = List.class),
    })
    @RequestMapping(value = "/location/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findLocationByEmail(@PathVariable("email") String email) {//TODO: email validation

        logger.info("+++++++++LOGGING findLocationByEmail+++++++++");
        String location = service.findLocationByEmail(email);
        logger.info("+++++++++SUCCESSFUL LOGGING findLocationByEmail+++++++++");
        return new ResponseEntity<>(location, HttpStatus.OK);
    }
}
