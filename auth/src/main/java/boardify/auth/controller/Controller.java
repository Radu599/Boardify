package boardify.auth.controller;

import boardify.auth.dto.UserDto;
import boardify.auth.dto.AuthenticationResponse;
import boardify.auth.service.Service;
import boardify.auth.service.exception.AuthExceptionType;
import boardify.auth.service.exception.AuthServiceException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/authenticate")
public class Controller {

    private final Logger logger = LogManager.getLogger(Controller.class);

    @Autowired
    private Service service;

    @ApiOperation(value = "Login a specific user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = AuthenticationResponse.class),
            @ApiResponse(code = 400, message = "INVALID_CREDENTIALS", response = AuthExceptionType.class),
            @ApiResponse(code = 404, message = "INVALID_CREDENTIALS", response = AuthExceptionType.class)
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@Valid UserDto user, BindingResult result){

        logger.info("+++++++++LOGGING login+++++++++");
        loggingUserDto(user);
        if (result.hasErrors())
            throw new AuthServiceException("Username or password for user: "+ user+" can not be null!", AuthExceptionType.INVALID_CREDENTIALS,HttpStatus.BAD_REQUEST);

        AuthenticationResponse response = service.login(user.getUsername(),user.getPassword());
//        logger.info("MyUser {} has role: {}",user.getUsername(),response.getRole());
        logger.info("+++++++++SUCCESSFUL LOGGING login+++++++++");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "test")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = AuthenticationResponse.class),
            @ApiResponse(code = 400, message = "INVALID_CREDENTIALS", response = AuthExceptionType.class),
            @ApiResponse(code = 404, message = "INVALID_CREDENTIALS", response = AuthExceptionType.class)
    })

    private void loggingUserDto(UserDto userDto){

        logger.info("Username: {}", userDto.getUsername());
        logger.info("Password: {}", userDto.getPassword());
    }

    @ExceptionHandler({AuthServiceException.class})
    @ResponseBody
    public ResponseEntity<AuthExceptionType> handleException(AuthServiceException exception) {

        logger.error("+++++++++LOGGING handleException+++++++++");
        logger.error(exception.getMessage());
        logger.error("+++++++++END LOGGING handleException+++++++++");
        return new ResponseEntity<>(exception.getType(), new HttpHeaders(), exception.getHttpStatus());
    }
}
