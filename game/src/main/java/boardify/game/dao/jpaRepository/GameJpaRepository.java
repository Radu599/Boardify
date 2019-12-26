package boardify.game.dao.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

interface GameJpaRepository extends JpaRepository<GamePersistence, String> {

}
