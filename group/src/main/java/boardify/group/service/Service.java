package boardify.group.service;

import boardify.group.dto.UserDto;
import boardify.group.model.GameGroup;
import boardify.group.model.GroupMember;

import java.util.List;

public interface Service {

    List<UserDto> findGroupForUser(String email);
    int findSizeForGroup(int gameGroupID);
    List<GameGroup> findAllGameGroups();
    void saveGroupMember(GroupMember groupMember);
}
