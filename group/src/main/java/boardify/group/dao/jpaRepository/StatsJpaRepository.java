package boardify.group.dao.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

interface StatsJpaRepository extends JpaRepository<StatsPersistance, String> {

    StatsPersistance findStatsByGroupIdAndEmail(int groupId, String email);
}
