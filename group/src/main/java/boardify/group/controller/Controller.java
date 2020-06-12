package boardify.group.controller;


import boardify.group.dto.UserDto;
import boardify.group.model.GameGroup;
import boardify.group.model.GroupMember;
import boardify.group.service.GameGroupSearcher;
import boardify.group.service.Service;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/groups")
public class Controller {

    private final Logger logger = LogManager.getLogger();

    private final GameGroupSearcher gameGroupSearcher;
    private final Service service;
    //TODO: autowired missing?
    public Controller(GameGroupSearcher gameGroupSearcher, Service service) {
        this.gameGroupSearcher = gameGroupSearcher;
        this.service = service;
    }

    @ApiOperation(value = "Find group for user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = List.class),
    })
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> findGroupForUser(Principal principal) {

        logger.info("LOG START - findGroupForUser");
        String email = principal.getName();
        List<UserDto> userDtos = service.findGroupForUser(email);
        logger.info("LOG FINISH - findGroupForUser");
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Find group for user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = List.class),
    })
    @RequestMapping(value = "/joinGame/{gameId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameGroup>> joinGame(Principal principal, @PathVariable("gameId") String gameId) {

        logger.info("LOG START - joinGame");
        String email = principal.getName();
        List<GameGroup> openGroups = findOpenGroups(Integer.valueOf(gameId));
        if(openGroups.size()>0){
            int gameGroupId = openGroups.get(0).getId();
            service.saveGroupMember(new GroupMember(email, gameGroupId));
            //notify user
        }
        else{

        }
        logger.info("LOG FINISH - joinGame");
        return new ResponseEntity<>(openGroups, HttpStatus.OK);
    }

    private List<GameGroup> findOpenGroups(int gameId) {

        return service.findAllGameGroups()
                .stream()
                .filter(gameGroup -> gameGroup.getGameId() == gameId
                        && service.findSizeForGroup(gameGroup.getId()) < gameGroupSearcher.getMinimumNumberOfPlayers(gameGroup.getGameId()))
                .collect(Collectors.toList());
    }
}
