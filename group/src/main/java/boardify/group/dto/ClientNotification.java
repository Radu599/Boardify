package boardify.group.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClientNotification {

    private String data;
    private ClientNotificationType type;

    public ClientNotification(ClientNotificationType type){
        this.type = type;
    }
}
