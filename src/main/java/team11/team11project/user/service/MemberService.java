package team11.team11project.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team11.team11project.common.encode.PasswordEncoder;
import team11.team11project.common.entity.Member;
import team11.team11project.common.exception.DuplicateEmailException;
import team11.team11project.common.exception.IncorrectPasswordException;
import team11.team11project.common.exception.NotFoundEmailException;
import team11.team11project.common.utils.JwtUtil;
import team11.team11project.user.model.request.LoginRequest;
import team11.team11project.user.model.request.RegisterRequest;
import team11.team11project.user.model.response.LoginDto;
import team11.team11project.user.model.response.RegisterDto;
import team11.team11project.user.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public RegisterDto registerMember(RegisterRequest request) {
        //비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = Member.createMember(request, encodedPassword);

        //이메일 중복 검사
        boolean validateEmail = memberRepository.existsByEmail(request.getEmail());
        if (validateEmail) {
            throw new DuplicateEmailException("이미 가입된 이메일 입니다.");
        }

        memberRepository.save(member);
        return RegisterDto.convertDto(member);
    }

    //TODO : 공통 예외처리 작성해야함
    public LoginDto LoginMember(LoginRequest request) {
        // 유저 검증
        Member member = memberRepository.findMemberByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundEmailException("가입되지 않거나 이메일이 잘못되었습니다."));

        // 비밀번호 매칭
        boolean matches = passwordEncoder.matches(request.getPassword(), member.getPassword());
        if (!matches) {
            throw new IncorrectPasswordException("비밀번호가 일치하지 않습니다.");
        }

        // 유저 검증을 마쳤다면 토큰 생성해서 반환해줌.
        String token = jwtUtil.generateToken(member.getId(), member.getMemberName(), member.getUserRole());

        return LoginDto.convertDto(token);
    }
}
