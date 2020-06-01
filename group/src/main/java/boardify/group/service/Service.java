package boardify.group.service;

import boardify.group.dto.UserDto;
import boardify.group.model.GameGroup;
import boardify.group.model.GroupMember;

import java.util.List;

public interface Service {

    //group member
    void saveGroupMember(GroupMember groupMember);

    //game group
    int findSizeForGroup(int gameGroupID);
    List<UserDto> findGroupForUser(String email);
    List<GameGroup> findAllGameGroups();
    int saveGroup(GameGroup gameID);

    int findGameForGroup(int groupID);
}
