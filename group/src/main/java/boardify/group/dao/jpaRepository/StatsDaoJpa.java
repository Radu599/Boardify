package boardify.group.dao.jpaRepository;

import boardify.group.dao.StatsDao;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@Component
@EnableJpaRepositories(basePackageClasses = StatsJpaRepository.class)
public class StatsDaoJpa implements StatsDao {

    private StatsJpaRepository statsJpaRepository;
}
