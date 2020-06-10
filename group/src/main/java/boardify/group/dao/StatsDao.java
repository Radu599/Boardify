package boardify.group.dao;

import boardify.group.model.Stats;

import java.util.List;

public interface StatsDao {

    Stats findStatsByGroupIdAndEmail(int groupId, String email);

    void saveStats(Stats stats);

    void updateStats(int groupId, String email, long lastMessage, long messageCount);

    List<Stats> findStatsByGroupId(int groupId);
}
