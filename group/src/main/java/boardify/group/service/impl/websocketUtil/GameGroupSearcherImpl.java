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

    @Override
    public List<GameGroup> joinGame(String email, int gameId) {

        List<GameGroup> openGroups = findOpenGroups(Integer.valueOf(gameId));
        int gameGroupId = -1;

        if (openGroups.size() > 0)
            gameGroupId = openGroups.get(0).getId();
        else {
            gameGroupId = service.saveGroup(gameId);
            openGroups = findOpenGroups(Integer.valueOf(gameId));
        }

        service.saveGroupMember(new GroupMember(email, gameGroupId));

        return openGroups;
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
        // TODO: create config file or extract this from somewhere
        String localHost = "localhost";
        String dockerIp = "192.168.99.100";
        String development = "development";
        String ip = (environment.getActiveProfiles().equals(development))? localHost :(dockerIp);
        String gameApiUrl = "http://" + ip + ":8080/games/minimumNumberOfPlayers/" + gameId;
        logger.info("Requesting minimum number of players at url:" + gameApiUrl);
        logger.info("++++++++SUCCESSFULLY LOGGED getMinimumNumberOfPlayers+++++++++++");
        return restTemplate.getForObject(gameApiUrl, Integer.class); // TODO: also make port easily configurable if zuul fails
    }

    @Override
    public int findGameForGroup(int groupID) {
        return service.findGameForGroup(groupID);
    }
}
