package boardify.group.service;

import boardify.group.model.GameGroup;

import java.util.List;

public interface GameGroupSearcher {

    List<GameGroup> joinGame(String email, int gameId);
    int getMinimumNumberOfPlayers(int gameId);
    int findGameForGroup(int groupID);
}
