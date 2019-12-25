package boardify.service.impl;

import boardify.dao.GameDao;
import boardify.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceImpl implements Service {

    @Autowired
    private GameDao catalogDao;
}
