package boardify.group.dao;

import boardify.group.dto.UserDto;
import boardify.group.model.GroupMembers;

import java.util.List;

public interface GroupMembersDao {

    List<UserDto> findGroupForUser(String email);
}
