package boardify.controller;


import boardify.model.Game;
import boardify.service.Service;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
public class Controller {

    private final Logger logger = LogManager.getLogger();
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Service service;

    @ApiOperation(value = "Find all games")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = List.class),
    })
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Game>> findAllGames() {

        logger.info("+++++++++LOGGING findAllGames+++++++++");
        List<Game> entities = service.findAllGames();
        logger.info("+++++++++SUCCESSFUL LOGGING findAllGames+++++++++");
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }
}
