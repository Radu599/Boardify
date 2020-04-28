package boardify.group.dto.JoinGroupDto.ClientToServer;

import boardify.group.dto.Notification;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JoinGroupMessageFromClient extends Notification {

    private String email;
    private int gameId;
    private JoinGroupFromClientType type;
}
