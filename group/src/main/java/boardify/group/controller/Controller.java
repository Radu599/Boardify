package boardify.group.controller;


import boardify.group.dto.UserDto;
import boardify.group.service.Service;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class Controller {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private Service service;

    @ApiOperation(value = "Find group for user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = List.class),
    })
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> findGroupForUser(Principal principal) {

        logger.info("+++++++++LOGGING findAllGames+++++++++");
        String email = principal.getName();

        assert(email!=null);

        List<UserDto> userDtos = service.findGroupForUser(email);
        logger.info("+++++++++SUCCESSFUL LOGGING findAllGames+++++++++");
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
}
