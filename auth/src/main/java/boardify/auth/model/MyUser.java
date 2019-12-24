package boardify.auth.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MyUser {

    private String username;
    private String password;
    private String role;
}
