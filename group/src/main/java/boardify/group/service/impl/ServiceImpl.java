package boardify.group.service.impl;

import boardify.group.dao.GameGroupDao;
import boardify.group.dao.GroupMembersDao;
import boardify.group.dto.UserDto;
import boardify.group.model.GameGroup;
import boardify.group.model.GroupMember;
import boardify.group.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@org.springframework.stereotype.Service
@Primary
@Component
public class ServiceImpl implements Service {

    @Autowired
    private GameGroupDao groupDao;
    @Autowired
    private GroupMembersDao groupMembersDao;

    @Override
    public List<UserDto> findGroupForUser(String email) {
        return groupMembersDao.findGroupForUser(email);
    }

    @Override
    public int findSizeForGroup(int gameGroupID) {

        return groupMembersDao.findGroupMembersByGameGroupID(gameGroupID).size();
    }

    @Override
    public List<GameGroup> findAllGameGroups() {

        return groupDao.findAllGameGroups();
    }

    @Override
    public int saveGroup(GameGroup gameGroup) {

        return groupDao.save(gameGroup);
    }

    @Override
    public int findGameForGroup(int groupID) {
        return groupDao.findGameForGroup(groupID);
    }

    @Override
    public void saveGroupMember(GroupMember groupMember) {

        groupMembersDao.save(groupMember);
    }
}
