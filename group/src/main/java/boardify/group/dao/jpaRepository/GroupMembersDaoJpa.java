package boardify.group.dao.jpaRepository;

import boardify.group.dao.GroupMembersDao;
import boardify.group.dto.UserDto;
import boardify.group.model.GroupMembers;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.User;
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
        List<GroupMembers> groupMembers = findGroupMembersByGameGroupID(gameGroupID);

        // find emails of all group members
        List<String> groupMembersEmails = groupMembers.stream()
                                            .map(GroupMembers::getUser_email)
                                            .collect(Collectors.toList());

        // refactor
        List<UserDto> result = new ArrayList<>();
        groupMembersEmails.forEach(userEmail -> result.add(UserDto.builder().email(userEmail).build()));

        return result;
    }

    public List<GroupMembers> findGroupMembersByGameGroupID(int gameGroupID) {

        return groupMembersJpaRepository.findByGameGroupID(gameGroupID)
                .stream()
                .map(this::convertGroupMembersPersitanceToGroupMembers)
                .collect(Collectors.toList());
    }

    private GroupMembers convertGroupMembersPersitanceToGroupMembers(GroupMembersPersistance groupMembersPersistance) {

        return groupMembersPersistance == null ? null : GroupMembers
                .builder()
                .user_email(groupMembersPersistance.getUserEmail())
                .gameGroupId(groupMembersPersistance.getGameGroupID())
                .build();
    }

    public int findGameGroupID(String email) {

        return groupMembersJpaRepository.findById(email).get().getGameGroupID();
    }
}
