package boardify.group.dto.JoinGroupDto.ServerToClient;

import boardify.group.dto.Notification;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClientNotification extends Notification {

    private int count;
    private int groupId;
    private ClientNotificationType type;

    public ClientNotification(ClientNotificationType type){
        this.type = type;
    }
}
