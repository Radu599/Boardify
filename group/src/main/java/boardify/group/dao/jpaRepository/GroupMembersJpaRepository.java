package boardify.group.dao.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMembersJpaRepository extends JpaRepository<GroupMembersPersistance, String> {

    List<GroupMembersPersistance> findByGameGroupID(int gameGroupID);
}
