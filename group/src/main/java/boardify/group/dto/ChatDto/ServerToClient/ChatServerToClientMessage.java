package boardify.group.dto.ChatDto.ServerToClient;

import boardify.group.dto.Notification;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatServerToClientMessage extends Notification {

    private String email;
    private String message;
    private int targetGroup;
    private ChatServerToClientMessageType type;
}
