package com.likelion.likit.member;

import com.likelion.likit.exception.CustomException;
import com.likelion.likit.exception.ExceptionEnum;
import com.likelion.likit.member.dto.*;
import com.likelion.likit.member.entity.Member;
import com.likelion.likit.member.entity.MemberDetail;
import com.likelion.likit.member.repository.JpaMemberDetailRepository;
import com.likelion.likit.member.repository.JpaMemberRepository;
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
    private final JpaMemberRepository jpaMemberRepository;
    private final JpaMemberDetailRepository jpaMemberDetailRepository;

    @Operation(summary = "회원가입", description = "성공하면 memberID 반환")
    @PostMapping("/member/new")
    public void createMember(@RequestBody MemberReqDto memberReqDto) {

        if (jpaMemberRepository.findByStudentId(memberReqDto.getStudentId()).isPresent()) {
            throw new CustomException(ExceptionEnum.STUDENTIDISPRESENT);
        }
        if (jpaMemberDetailRepository.findByEmail(memberReqDto.getEmail()).isPresent()) {
            throw new CustomException(ExceptionEnum.EMAILISPRESENT);
        }

        String encodePassword = passwordEncoder.encode(memberReqDto.getPassword());
        Member member = memberReqDto.toEntity(encodePassword);
        memberService.join(member);
        MemberDetail memberDetail = memberReqDto.detailEntity(member);
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

    @Operation(summary = "비밀번호 확인", description = "성공하면 회원정보 반환")
    @PostMapping("/member/check")
    public ResponseEntity<MemberResDto> checkPassword(@RequestHeader String accessToken,
                                 @RequestBody String password) {
        Member member = findMemberByToken(accessToken);
        if (passwordEncoder.matches(password, member.getPassword())) {
            return ResponseEntity.ok(new MemberResDto(member));
        } else {
            throw new CustomException(ExceptionEnum.PasswordNotMatched);
        }
    }

    @Operation(summary = "회원정보 조회", description = "Header에 accessToken 필수! \n 성공하면 회원 정보 반환")
    @GetMapping("/member/{studentId}")
    public ResponseEntity<MemberResDto> viewUserInfo(@PathVariable String studentId) {

        return ResponseEntity.ok(memberService.findMember(studentId));
    }

    @Operation(summary = "회원 정보 수정", description = "Header에 accessToken 필수! \n 성공하면 회원 정보 반환")
    @PatchMapping("/member")
    public ResponseEntity<MemberResDto> updateUserInfo(@RequestHeader String accessToken,
                                                       @RequestPart(value = "update", required = false) MemberUpdateReqDto memberUpdateReqDto,
                                                       @RequestPart(value = "tech", required = false) TechUpdateDto techUpdateDto) {

        Member member = findMemberByToken(accessToken);
        return ResponseEntity.ok(memberService.update(member, memberUpdateReqDto, techUpdateDto));
    }

    @Operation(summary = "회원 정보 삭제", description = "Header에 accessToken 필수!")
    @DeleteMapping("/member")
    public void deleteUserInfo(@RequestHeader String accessToken) {
        Member member = findMemberByToken(accessToken);
        memberService.delete(member);
    }

    public Member findMemberByToken(String accessToken) {
        String studentId = tokenService.getStudentIdFromToken(accessToken);
        Member member = memberService.findByStudentId(studentId);
        return member;
    }


}
