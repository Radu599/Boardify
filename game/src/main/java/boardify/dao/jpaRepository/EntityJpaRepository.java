package boardify.dao.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

interface EntityJpaRepository extends JpaRepository<GamePersistence, String> {

}
