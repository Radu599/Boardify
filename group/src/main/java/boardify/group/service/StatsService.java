package boardify.group.service;

import boardify.group.model.Stats;

import java.util.List;

public interface StatsService {

    void saveStats(Stats stats);
    List<Stats> findStatsByGroupId(int groupId);
    Stats findStatsByGroupIdAndEmail(int groupId, String email);
}
