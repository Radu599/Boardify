package boardify.auth.controller;

import boardify.auth.dto.AuthenticationResponse;
import boardify.auth.dto.UserDto;
import boardify.auth.service.Service;
import boardify.auth.service.exception.LoginExceptionType;
import boardify.auth.service.exception.LoginServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/authenticate")
public class Controller {

    private final Logger logger = LogManager.getLogger(Controller.class);

    @Autowired
    private Service service;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@Valid UserDto user, BindingResult result){

        logger.info("LOG START - login");

        if (result.hasErrors())
            throw new LoginServiceException("Username or password for user: "+ user+" can not be null!", LoginExceptionType.INVALID_CREDENTIALS,HttpStatus.BAD_REQUEST);

        AuthenticationResponse response = service.login(user.getUsername(),user.getPassword());
        logger.info("LOG FINISH - login");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler({LoginServiceException.class})
    @ResponseBody
    public ResponseEntity<LoginExceptionType> handleException(LoginServiceException exception) {

        logger.error("LOG START - handleException");
        logger.error(exception.getMessage());
        logger.error("LOG FINISH - handleException");
        return new ResponseEntity<>(exception.getLoginExceptionType(), new HttpHeaders(), exception.getHttpStatus());
    }
}
