package boardify.group.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Stats implements Serializable {

    private int groupId;
    private String email;
    private long lastMessage;
    private long messageCount;
}
