package boardify.user.model;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {

    private String username;
    private String password;
    private String location;
    private String avatarPath;
    private String role;
}
