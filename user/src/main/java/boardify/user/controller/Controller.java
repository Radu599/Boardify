package boardify.user.controller;

import boardify.user.dto.RegisterResponse;
import boardify.user.dto.UserDto;
import boardify.user.model.User;
import boardify.user.service.Service;
import boardify.user.service.exception.UserExceptionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class Controller {

    private final Logger logger = LogManager.getLogger(Controller.class);

    @Autowired
    private Service service;

    @RequestMapping(value = "/location/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findLocationByEmail(@PathVariable("email") String email) {//TODO: email validation

        logger.info("LOG START - findLocationByEmail");
        String location = service.findLocationByEmail(email);
        logger.info("LOG FINISH - findLocationByEmail");
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @RequestMapping(value = "/location/{location}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserExceptionType> updateLocation(Principal principal, @PathVariable String location) {

        String email = principal.getName();

        //  assert(email!=null);

        logger.info("LOG START - updateLocation");
        //loggingEntity(user);
        service.updateLocation(email, location);
        logger.info("LOG FINISH - updateLocation");
        return new ResponseEntity<UserExceptionType>(UserExceptionType.SUCCESS, HttpStatus.OK);
    }

    //TODO: test
    @PostMapping("/uploadAvatar")
    public ResponseEntity<UserExceptionType> uploadAvatar(Principal principal, @RequestParam("imageFile") MultipartFile imageFile) {

        logger.info("LOG START - uploadAvatar");
        String email = principal.getName();

        try {
            service.saveAvatar(imageFile, email);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error saving photo", e);
        }
        logger.info("LOG FINISH - uploadAvatar");
        return new ResponseEntity<>(UserExceptionType.SUCCESS, HttpStatus.OK);
    }

    //TODO: test
    @RequestMapping("/downloadAvatar")
    public ResponseEntity downloadAvatar(@RequestParam String fileName) throws IOException {

        //TODO: refactor this
        File file = new File(fileName); // TODO: use the right path when debugging
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findUser(@PathVariable("email") String email) {//TODO: email validation

        logger.info("LOG START - findUserByEmail");
        User user = service.findUser(email);
        logger.info("LOG FINISH - findUserByEmail");
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<RegisterResponse> register(@Valid UserDto user){

        logger.info("LOG START - register");

        RegisterResponse registerResponse = service.registerUser(user.getUsername(),user.getPassword());
        logger.info("LOG FINISH - register");
        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }
}
