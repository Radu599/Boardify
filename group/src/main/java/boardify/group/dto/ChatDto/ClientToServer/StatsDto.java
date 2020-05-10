package boardify.group.dto.ChatDto.ClientToServer;

import boardify.group.dto.Notification;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StatsDto extends Notification implements Serializable {

    private String email;
    private int groupId;
    private long lastMessage;
    private long messageCount;
    private ChatClientToServerMessageType type = ChatClientToServerMessageType.STATS;
}
