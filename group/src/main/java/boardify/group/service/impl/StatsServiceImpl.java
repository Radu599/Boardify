package boardify.group.service.impl;

import boardify.group.dao.StatsDao;
import boardify.group.model.Stats;
import boardify.group.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@org.springframework.stereotype.Service
@Primary
@Component
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsDao statsDao;

    @Override
    public void saveStats(Stats stats) {

        Stats statsByGroupIdAndEmail = statsDao.findStatsByGroupIdAndEmail(stats.getGroupId(), stats.getEmail());

        if (statsByGroupIdAndEmail == null) // save
                statsDao.saveStats(stats);
        else { //update
            statsByGroupIdAndEmail.setMessageCount(statsByGroupIdAndEmail.getMessageCount() + 1);
            statsDao.updateStats(stats.getGroupId(), stats.getEmail(), stats.getLastMessage(), statsByGroupIdAndEmail.getMessageCount());
        }
    }
}
