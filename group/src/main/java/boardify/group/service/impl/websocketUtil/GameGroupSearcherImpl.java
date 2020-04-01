package boardify.group.service.impl.websocketUtil;

import boardify.group.service.GameGroupSearcher;
import boardify.group.service.Service;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class GameGroupSearcherImpl implements GameGroupSearcher {

    @Autowired
    private Service service;
    private String email;
    private int gameId;

    @Override
    public int findGameGroup(String email, int gameId) {
        //TODO:

        return 0;
    }
}
