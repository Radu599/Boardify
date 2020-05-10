package boardify.group.dao.jpaRepository;

import boardify.group.dao.StatsDao;
import boardify.group.model.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableJpaRepositories(basePackageClasses = StatsJpaRepository.class)
public class StatsDaoJpa implements StatsDao {

    @Autowired
    private StatsJpaRepository statsJpaRepository;

    @Override
    public Stats findStatsByGroupIdAndEmail(int groupId, String email) {

        return convertStatsPersistenceToStats(statsJpaRepository.findStatsByGroupIdAndEmail(groupId, email));
    }

    @Override
    public void saveStats(Stats stats) {

        statsJpaRepository.save(convertStatsToStatePersistence(stats));
    }

    @Override
    public void updateStats(int groupId, String email, long lastMessage, long messageCount) {

        StatsPersistance oldStats = statsJpaRepository.findStatsByGroupIdAndEmail(groupId, email);
        if (oldStats == null) {
            return;
        }
        oldStats = StatsPersistance.builder()
                .groupId(oldStats.getGroupId())
                .email(oldStats.getEmail())
                .messageCount(oldStats.getMessageCount())
                .lastMessage(oldStats.getLastMessage())
                .build();

        StatsPersistance stats = StatsPersistance.builder()
                .email(email)
                .groupId(oldStats.getGroupId())
                .lastMessage(lastMessage)
                .messageCount(messageCount)
                .build();
        // TODO: update
        statsJpaRepository.delete(oldStats);
        statsJpaRepository.save(stats);
    }

    @Override
    public List<Stats> findStatsByGroupId(int groupId) {
        return statsJpaRepository.findStatsByGroupId(groupId)
                .stream()
                .map(this::convertStatsPersistenceToStats)
                .collect(Collectors.toList());
    }

    private Stats convertStatsPersistenceToStats(StatsPersistance statsPersistance) {

        return statsPersistance == null ? null : Stats
                .builder()
                .email(statsPersistance.getEmail())
                .groupId(statsPersistance.getGroupId())
                .lastMessage(statsPersistance.getLastMessage())
                .messageCount(statsPersistance.getMessageCount())
                .build();
    }

    private StatsPersistance convertStatsToStatePersistence(Stats stats) {

        return stats == null ? null : StatsPersistance.builder()
                .email(stats.getEmail())
                .groupId(stats.getGroupId())
                .lastMessage(stats.getLastMessage())
                .messageCount(stats.getMessageCount())
                .build();
    }
}
