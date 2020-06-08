package boardify.group.service;

import boardify.group.model.GameGroup;

import java.util.List;

public interface GameGroupSearcher {

    List<GameGroup> joinGame(String email, int gameId, String city);
    int getMinimumNumberOfPlayers(int gameId);
    int findGameForGroup(int groupID);
}
