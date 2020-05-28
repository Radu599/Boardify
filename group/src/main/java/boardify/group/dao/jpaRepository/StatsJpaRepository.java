package boardify.group.dao.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface StatsJpaRepository extends JpaRepository<StatsPersistance, String> {

    StatsPersistance findStatsByGroupIdAndEmail(int groupId, String email);
    List<StatsPersistance> findStatsByGroupId(int groupId);
}
