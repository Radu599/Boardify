package boardify.group.dao;

import boardify.group.model.GameGroup;

import java.util.List;

public interface GameGroupDao {

    List<GameGroup> findAllGameGroups();
}
