package boardify.group.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Message {

    private String email;
    private int gameId;
    private MessageType type;
}
