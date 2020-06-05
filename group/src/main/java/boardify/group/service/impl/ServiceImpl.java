package boardify.group.service.impl;

import boardify.group.dao.GameGroupDao;
import boardify.group.dao.GroupMembersDao;
import boardify.group.dto.UserDto;
import boardify.group.model.GameGroup;
import boardify.group.model.GroupMember;
import boardify.group.service.GameGroupSearcher;
import boardify.group.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@org.springframework.stereotype.Service
@Primary
@Component
@Transactional // TODO: I used this because of custom deleteBy in dao: https://stackoverflow.com/questions/32269192/spring-no-entitymanager-with-actual-transaction-available-for-current-thread
public class ServiceImpl implements Service {

    @Autowired
    private GameGroupDao groupDao;
    @Autowired
    private GroupMembersDao groupMembersDao;
    @Autowired
    private GameGroupSearcher gameGroupSearcher;

    private final Logger logger = LogManager.getLogger();

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
    public void deleteGameGroup(int groupId) {
        groupDao.deleteById(groupId);
    }

    @Override
    public void deleteGroupMember(int groupMember) {
        groupMembersDao.deleteByGroupId(groupMember);
    }

    @Override
    public void deleteGroupMember(GroupMember groupMember) {
        groupMembersDao.deleteByGroupId(groupMember);
    }

    @Override
    public boolean groupIsPlaying(int groupId) {
        logger.info("Logging START groupIsPlaying");
        int groupSize = groupMembersDao.findSizeForGroup(groupId);
        logger.info("groupSize=" + groupSize);
        int gameId = groupDao.findGameForGroup(groupId);
        logger.info("gameId=" + gameId);
        int groupCapacity = gameGroupSearcher.getMinimumNumberOfPlayers(gameId);
        logger.info("groupCapacity=" + groupCapacity);
        return groupSize>=groupCapacity;
    }

    @Override
    public void saveGroupMember(GroupMember groupMember) {
        groupMembersDao.save(groupMember);
    }
}
