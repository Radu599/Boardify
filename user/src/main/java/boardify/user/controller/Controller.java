package boardify.user.controller;

import boardify.user.service.Service;
import boardify.user.service.exception.UserExceptionType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    //TODO: test
    @PostMapping("/uploadAvatar")
    public ResponseEntity<UserExceptionType> uploadAvatar(Principal principal, @RequestParam("imageFile") MultipartFile imageFile) {

        logger.info("+++++++++LOGGING uploadAvatar+++++++++");
        String email = principal.getName();

        try {
            service.saveAvatar(imageFile, email);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error saving photo", e);
        }
        logger.info("+++++++++SUCCESSFUL LOGGING uploadAvatar+++++++++");
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
}
