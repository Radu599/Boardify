package boardify.group.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GroupMember implements Serializable {

    String userEmail;
    int gameGroupID;
}
