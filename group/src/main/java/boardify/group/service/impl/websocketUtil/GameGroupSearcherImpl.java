package boardify.group.service.impl.websocketUtil;

import boardify.group.model.GameGroup;
import boardify.group.model.GroupMember;
import boardify.group.service.GameGroupSearcher;
import boardify.group.service.Service;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    private String email;
    private int gameId;

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
        return restTemplate.getForObject("http://localhost:8083/games/minimumNumberOfPlayers/" + gameId, Integer.class);
    }

    @Override
    public int findGameForGroup(int groupID) {
        return service.findGameForGroup(groupID);
    }
}
