package com.likelion.likit.member;

import com.likelion.likit.exception.CustomException;
import com.likelion.likit.exception.ExceptionEnum;
import com.likelion.likit.member.dto.LoginReqDto;
import com.likelion.likit.member.dto.MeberUpdateReqDto;
import com.likelion.likit.member.dto.MemberReqDto;
import com.likelion.likit.member.dto.MemberResDto;
import com.likelion.likit.member.entity.Member;
import com.likelion.likit.member.entity.MemberDetail;
import com.likelion.likit.token.Token;
import com.likelion.likit.token.TokenDto;
import com.likelion.likit.token.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequiredArgsConstructor
public class MemberController{
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Operation(summary = "회원가입", description = "성공하면 memberID 반환")
    @PostMapping("/member/new")
    public void createMember(@RequestBody MemberReqDto memberReqDto) {
        String encodePassword = passwordEncoder.encode(memberReqDto.getPassword());
        Member member = memberReqDto.toEntity(encodePassword);
        memberService.join(member);
        MemberDetail memberDetail = memberReqDto.detailEntity();
        memberService.saveDetail(memberDetail);
        Token token = new Token(member);
        tokenService.join(token);
    }

    @Operation(summary = "로그인", description = "성공하면 Token 반환")
    @PostMapping("/member/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginReqDto loginReqDto) {
        Member member = memberService.findByStudentId(loginReqDto.getStudentId());
        if (passwordEncoder.matches(loginReqDto.getPassword(), member.getPassword())) {
            return ResponseEntity.ok(tokenService.makeToken(member));
        } else {
            throw new CustomException(ExceptionEnum.PasswordNotMatched);
        }
    }

    @Operation(summary = "회원정보 조회", description = "Header에 accessToken 필수! \n 성공하면 회원 정보 반환")
    @GetMapping("/member")
    public ResponseEntity<MemberResDto> viewUserInfo(@RequestHeader String accessToken) {
        Member member = findMemberByToken(accessToken);
        return ResponseEntity.ok(new MemberResDto(member));
    }

    @Operation(summary = "회원 정보 수정", description = "Header에 accessToken 필수! \n 성공하면 회원 정보 반환")
    @PatchMapping("/member")
    public ResponseEntity<MemberResDto> updateUserInfo(@RequestHeader String accessToken,
                                                       @RequestBody MeberUpdateReqDto meberUpdateReqDto) {
        Member member = findMemberByToken(accessToken);
        return ResponseEntity.ok(memberService.update(member, meberUpdateReqDto));
    }

    @Operation(summary = "회원 정보 삭제", description = "Header에 accessToken 필수!")
    @DeleteMapping("/member")
    public void deleteUserInfo(@RequestHeader String accessToken) {
        Member member = findMemberByToken(accessToken);
        memberService.delete(member);
    }

    private Member findMemberByToken(String accessToken) {
        String studentId = tokenService.getStudentIdFromToken(accessToken);
        Member member = memberService.findByStudentId(studentId);
        return member;
    }


}
