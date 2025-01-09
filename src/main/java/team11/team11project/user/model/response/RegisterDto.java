package team11.team11project.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team11.team11project.common.entity.Member;
import team11.team11project.common.enums.UserRole;

@AllArgsConstructor
@Getter
public class RegisterDto {

    private Long id;
    private String MemberName;
    private String email;
    private UserRole userRole;

    public static RegisterDto convertDto(Member member) {
        return new RegisterDto(
                member.getId(),
                member.getMemberName(),
                member.getEmail(),
                member.getUserRole()
        );
    }
}
