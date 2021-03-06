package boardify.group.dto.ChatDto.ClientToServer;

import boardify.group.dto.Notification;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatClientToServerMessage extends Notification {

    private String senderEmail; //TODO: refact
    private String message;
    private int targetGroup;
    private ChatClientToServerMessageType type;
    private long timestamp;
}
