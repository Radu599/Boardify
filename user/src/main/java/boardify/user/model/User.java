package boardify.user.model;
import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private String email;
    private String password;
    private String location;
    private String avatarPath;
}
