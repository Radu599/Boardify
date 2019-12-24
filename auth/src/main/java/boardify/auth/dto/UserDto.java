package boardify.auth.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

    @NotNull (message = "Username is null!")
    private String username;
    @NotNull (message = "Password is null!")
    private String password;
}
