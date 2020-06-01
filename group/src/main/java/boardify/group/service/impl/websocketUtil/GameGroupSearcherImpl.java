package boardify.group.service.impl.websocketUtil;

import boardify.group.model.GameGroup;
import boardify.group.model.GroupMember;
import boardify.group.service.GameGroupSearcher;
import boardify.group.service.Service;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class GameGroupSearcherImpl implements GameGroupSearcher {

    @Autowired
    private Service service;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    private final Logger logger = LogManager.getLogger();

    private List<GameGroup> filterGameGroupsByCity(List<GameGroup> gameGroups, String city) {

        return gameGroups.stream()
                .filter(gameGroup ->gameGroup.getCity().equals(city))
                .collect(Collectors.toList());
    }

    //TODO: refactor this
    @Override
    public List<GameGroup> joinGame(String email, int gameId, String city) {

        logger.info("+++++++++ LOGGING joinGame ++++++");
        logger.info(String.format("Searching game for email=%s gameId=%2d city=%s", email, gameId, city));

        int gameGroupId;
        List<GameGroup> groupsInCity = filterGameGroupsByCity(findOpenGroups(Integer.valueOf(gameId)), city);

        if (groupsInCity.size() > 0) {
            logger.info(String.format("Active group found!"));
            gameGroupId = groupsInCity.get(0).getId();
        } else {
            logger.info(String.format("There is no active group. Creating one..."));
            gameGroupId = service.saveGroup(new GameGroup(gameId, city));
            groupsInCity = filterGameGroupsByCity(findOpenGroups(Integer.valueOf(gameId)), city);
        }

        service.saveGroupMember(new GroupMember(email, gameGroupId));
        logger.info("+++++++++ LOGGING SUCCESSFULLY joinGame ++++++");
        return groupsInCity;
    }

    private List<GameGroup> findOpenGroups(int gameId) {

        return service.findAllGameGroups()
                .stream()
                .filter(gameGroup -> gameGroup.getGameId() == gameId
                        && service.findSizeForGroup(gameGroup.getId()) < getMinimumNumberOfPlayers(gameGroup.getGameId()))
                .collect(Collectors.toList());
    }

    @Override
    public int getMinimumNumberOfPlayers(int gameId) {

        logger.info("++++++++LOGGING getMinimumNumberOfPlayers+++++++++++");
        // TODO: create config file or extract this from it
        String localHost = "localhost";
        String dockerIp = "192.168.99.100";
        String development = "development";
        String gamePort = ":8083";

        boolean runProduction = Arrays.stream(environment.getActiveProfiles()).anyMatch(profile -> profile.equals("{production}"));
        String ip = !runProduction ? localHost : (dockerIp);
        String gameApiUrl = "http://" + ip + gamePort + "/games/minimumNumberOfPlayers/" + gameId;
        logger.info("Requesting minimum number of players at url:" + gameApiUrl);
        logger.info("++++++++SUCCESSFULLY LOGGED getMinimumNumberOfPlayers+++++++++++");
        return restTemplate.getForObject(gameApiUrl, Integer.class); // TODO: also make port easily configurable if zuul fails
    }

    @Override
    public int findGameForGroup(int groupID) {
        return service.findGameForGroup(groupID);
    }
}
