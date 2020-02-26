package boardify.group.service.impl;

import boardify.group.dao.GroupMembersDao;
import boardify.group.dto.UserDto;
import boardify.group.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

//TODO:
@org.springframework.stereotype.Service
@Primary
@Component
public class ServiceImpl implements Service {

    //@Autowired
    //private GameGroupDao groupDao;
    @Autowired
    private GroupMembersDao groupMembersDao;

    @Override
    public List<UserDto> findGroupForUser(String email) {
        return groupMembersDao.findGroupForUser(email);
    }
}
