package boardify.auth.dao.impl.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository extends JpaRepository<UserPersistence, String> {

}
