package boardify.group.service.impl.websocketUtil;

import boardify.group.model.GameGroup;
import boardify.group.model.GroupMember;
import boardify.group.service.GameGroupSearcher;
import boardify.group.service.Service;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Component
public class GameGroupSearcherImpl implements GameGroupSearcher {

    private final Logger logger = LogManager.getLogger();

    private Service service;
    private RestTemplate restTemplate;
    private Environment environment;

    @Autowired
    public GameGroupSearcherImpl(Service service, RestTemplate restTemplate, Environment environment) {
        this.service = service;
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    private List<GameGroup> filterGameGroupsByCity(List<GameGroup> gameGroups, String city) {

        return gameGroups.stream()
                .filter(gameGroup ->gameGroup.getCity().equals(city))
                .collect(Collectors.toList());
    }

    //TODO: refactor this
    @Override
    public List<GameGroup> joinGame(String email, int gameId, String city) {

        logger.info("LOG START - joinGame");
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
        logger.info("LOG FINISH - joinGame");
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

        logger.info("LOG START - getMinimumNumberOfPlayers");
        // TODO: create config file or extract this from it
        String localHost = "localhost";
        String dockerIp = "192.168.99.100";
        String development = "development";
        String gamePort = ":8083";

        boolean runInDevelopment = Arrays.stream(environment.getActiveProfiles()).anyMatch(profile -> profile.equals("{development}"));
        String ip = runInDevelopment ? localHost : (dockerIp);
        String gameApiUrl = "http://" + ip + gamePort + "/games/minimumNumberOfPlayers/" + gameId;
        logger.info("Requesting minimum number of players at url:" + gameApiUrl);
        logger.info("LOG FINISH - getMinimumNumberOfPlayers");
        return restTemplate.getForObject(gameApiUrl, Integer.class); // TODO: also make port easily configurable if zuul fails
    }

    @Override
    public int findGameForGroup(int groupID) {
        return service.findGameForGroup(groupID);
    }
}
