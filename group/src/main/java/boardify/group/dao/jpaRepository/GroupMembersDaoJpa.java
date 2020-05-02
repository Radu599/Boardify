package boardify.group.dao.jpaRepository;

import boardify.group.dao.GroupMembersDao;
import boardify.group.dto.UserDto;
import boardify.group.model.GroupMember;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableJpaRepositories(basePackageClasses = GroupMembersJpaRepository.class)
public class GroupMembersDaoJpa implements GroupMembersDao {

    private GroupMembersJpaRepository groupMembersJpaRepository;

    //private Logger logger = LogManager.getLogger(GameDaoJpa.class);

    public GroupMembersDaoJpa(GroupMembersJpaRepository jpaRepository) {
        this.groupMembersJpaRepository = jpaRepository;
    }

    @Override
    public List<UserDto> findGroupForUser(String email) {

        // find game group of this user
        int gameGroupID = findGameGroupID(email);

        // find all group members in his group
        List<GroupMember> groupMembers = findGroupMembersByGameGroupID(gameGroupID);

        // find emails of all group members
        List<String> groupMembersEmails = groupMembers.stream()
                                            .map(GroupMember::getUserEmail)
                                            .collect(Collectors.toList());

        // refactor
        List<UserDto> result = new ArrayList<>();
        groupMembersEmails.forEach(userEmail -> result.add(UserDto.builder().email(userEmail).build()));

        return result;
    }

    @Override
    public List<GroupMember> findGroupMembersByGameGroupID(int gameGroupID) {

        return groupMembersJpaRepository.findByGameGroupID(gameGroupID)
                .stream()
                .map(this::convertGroupMembersPersitanceToGroupMembers)
                .collect(Collectors.toList());
    }

    @Override
    public int findSizeForGroup(int gameGroupID) {

        return this.findGroupMembersByGameGroupID(gameGroupID).size();
    }


    private GroupMember convertGroupMembersPersitanceToGroupMembers(GroupMembersPersistance groupMembersPersistance) {

        return groupMembersPersistance == null ? null : GroupMember
                .builder()
                .userEmail(groupMembersPersistance.getUserEmail())
                .gameGroupID(groupMembersPersistance.getGameGroupID())
                .build();
    }

    @Override
    public int findGameGroupID(String email) {

        List<GroupMembersPersistance> groupMembersPersistanceList = groupMembersJpaRepository.findByUserEmail(email);
        if(groupMembersPersistanceList==null)
            return 0; // TODO: throw exception
        return groupMembersPersistanceList.get(0).getGameGroupID();
    }

    @Override
    public void save(GroupMember groupMember) {

        groupMembersJpaRepository.save(this.convertGroupMemberToGroupMemberPersistance(groupMember));
    }

    private GroupMembersPersistance convertGroupMemberToGroupMemberPersistance(GroupMember groupMember) {

        return groupMember == null ? null : GroupMembersPersistance.builder()
                .gameGroupID(groupMember.getGameGroupID())
                .userEmail(groupMember.getUserEmail())
                .build();
    }
}
