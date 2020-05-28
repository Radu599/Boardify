package boardify.auth.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
<<<<<<< HEAD:auth/src/main/java/boardify/auth/model/BoardifyUser.java
=======
@ToString
>>>>>>> develop:auth/src/main/java/boardify/auth/model/MyUser.java
public class BoardifyUser {

    private String username;
    private String password;
    private String role;
}
