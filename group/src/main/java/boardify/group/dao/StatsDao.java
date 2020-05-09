package boardify.group.dao;

import boardify.group.model.Stats;

public interface StatsDao {

    Stats findStatsByGroupIdAndEmail(int groupId, String email);
    void saveStats(Stats stats);
    void updateStats(int groupId, String email, long lastMessage, long messageCount);
}
