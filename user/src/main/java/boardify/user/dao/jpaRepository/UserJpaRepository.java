package boardify.user.dao.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository extends JpaRepository<UserPersistance, String> {

}
