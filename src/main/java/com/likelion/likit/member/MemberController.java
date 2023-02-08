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

    @PostMapping("/member/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginReqDto loginReqDto) {
        Member member = memberService.findByStudentId(loginReqDto.getStudentId());
        if (passwordEncoder.matches(loginReqDto.getPassword(), member.getPassword())) {
            return ResponseEntity.ok(tokenService.makeToken(member));
        } else {
            throw new CustomException(ExceptionEnum.PasswordNotMatched);
        }
    }

    @GetMapping("/member")
    public ResponseEntity<MemberResDto> viewUserInfo(@RequestHeader String accessToken) {
        Member member = findMemberByToken(accessToken);
        return ResponseEntity.ok(new MemberResDto(member));
    }

    @PatchMapping("/member")
    public ResponseEntity<MemberResDto> updateUserInfo(@RequestHeader String accessToken,
                                                       @RequestBody MeberUpdateReqDto meberUpdateReqDto) {
        Member member = findMemberByToken(accessToken);
        return ResponseEntity.ok(memberService.update(member, meberUpdateReqDto));
    }

    private Member findMemberByToken(String accessToken) {
        String studentId = tokenService.getStudentIdFromToken(accessToken);
        Member member = memberService.findByStudentId(studentId);
        return member;
    }


}
