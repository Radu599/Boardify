package boardify.auth.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

    @NotNull (message = "Username cannot be null!")
    private String username;
    @NotNull (message = "Password cannot be null!")
    private String password;
}
