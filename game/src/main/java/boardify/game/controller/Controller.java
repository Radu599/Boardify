package boardify.game.controller;


import boardify.game.model.Game;
import boardify.game.service.Service;
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/games")
public class Controller {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private Service service;

    @ApiOperation(value = "Find all games")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = List.class),
    })
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Game>> findAllGames(Principal principal) {

        logger.info(principal.getName() +  " called findAllGames()");
        logger.info("+++++++++LOGGING findAllGames+++++++++");
        List<Game> entities = service.findAllGames();
        logger.info("+++++++++SUCCESSFUL LOGGING findAllGames+++++++++");
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

        @ApiOperation(value = "Find all games")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "SUCCESS", response = List.class),
        })
        @RequestMapping(value = "/minimumNumberOfPlayers/{gameId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Integer> getMinimumNumberOfPlayers(@PathVariable("gameId") String gameId) {

            logger.info("+++++++++LOGGING getMinimumNumberOfPlayers+++++++++");
            int minimumNumberOfPlayers= service.getMinimumNumberOfPlayers(Integer.valueOf(gameId));
            logger.info("+++++++++SUCCESSFUL LOGGING getMinimumNumberOfPlayers+++++++++");
            return new ResponseEntity<Integer>(minimumNumberOfPlayers, HttpStatus.OK);
        }
}
