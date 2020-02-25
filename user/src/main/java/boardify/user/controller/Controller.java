package boardify.user.controller;

import boardify.user.service.Service;
import boardify.user.service.exception.UserExceptionType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @ApiOperation(value = "Update given user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = UserExceptionType.class),
            @ApiResponse(code = 404, message = "ENTITY_NOT_FOUND", response = UserExceptionType.class)
    })
    @RequestMapping(value = "/location/{location}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserExceptionType> updateLocation(Principal principal, @PathVariable String location) {

        String email = principal.getName();

        assert(email!=null);

        logger.info("+++++++++LOGGING updateLocation+++++++++");
        //loggingEntity(user);
        service.updateLocation(email, location);
        logger.info("+++++++++SUCCESSFUL LOGGING updateLocation+++++++++");
        return new ResponseEntity<UserExceptionType>(UserExceptionType.SUCCESS, HttpStatus.OK);
    }
}
