package boardify.group.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GroupMembers {

    String user_email;
    int gameGroupId;
}
