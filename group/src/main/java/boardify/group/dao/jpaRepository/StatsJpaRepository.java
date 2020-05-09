package boardify.group.dao.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

interface StatsJpaRepository extends JpaRepository<StatsPersistance, Integer> {

    StatsPersistance findStatsByGroupIdAndEmail(int groupId, String email);
}
