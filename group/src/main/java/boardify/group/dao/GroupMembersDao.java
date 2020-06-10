package boardify.group.dao;

import boardify.group.dto.UserDto;
import boardify.group.model.GroupMember;

import java.util.List;

public interface GroupMembersDao {

    List<UserDto> findGroupForUser(String email);

    List<GroupMember> findGroupMembersByGameGroupID(int gameGroupID);

    int findSizeForGroup(int gameGroupID);

    int findGameGroupID(String email);

    void save(GroupMember groupMember);

    void deleteByGroupId(int groupId);

    void deleteByGroupId(GroupMember groupMember);
}
