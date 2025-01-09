package team11.team11project.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginDto {

    private String token;

    public static LoginDto convertDto(String token) {
        return new LoginDto(token);
    }
}
