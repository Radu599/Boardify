package boardify.game.dao.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface GameJpaRepository extends JpaRepository<GamePersistence, String> {

    Optional<GamePersistence> findById(int gameId);
}
