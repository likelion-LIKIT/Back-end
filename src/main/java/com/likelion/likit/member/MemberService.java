package com.likelion.likit.member;

import com.likelion.likit.exception.CustomException;
import com.likelion.likit.exception.ExceptionEnum;
import com.likelion.likit.member.dto.MeberUpdateReqDto;
import com.likelion.likit.member.dto.MemberResDto;
import com.likelion.likit.member.entity.*;
import com.likelion.likit.member.repository.JpaMemberDetailRepository;
import com.likelion.likit.member.repository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService  {
    private final JpaMemberRepository jpaMemberRepository;
    private final JpaMemberDetailRepository jpaMemberDetailRepository;

    @Transactional
    public Member findByStudentId(String studentId) {
        Member member = jpaMemberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.StudentIdNotMatched));
        return member;
    }
    @Transactional
    public void join(Member member) {
        jpaMemberRepository.save(member);
    }

    @Transactional
    public void saveDetail(MemberDetail memberDetail) {
        jpaMemberDetailRepository.save(memberDetail);
    }


    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {
        return jpaMemberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.StudentIdNotMatched));
    }

    @Transactional
    public MemberResDto update(Member member, MeberUpdateReqDto meberUpdateReqDto) {

        Optional<String> updateStudentName = meberUpdateReqDto.getStudentName();
        Optional<String> updatePhoneNumber = meberUpdateReqDto.getPhoneNumber();
        Optional<String> updateDescription = meberUpdateReqDto.getDescription();
        Optional<Grade> updateGrade = meberUpdateReqDto.getGrade();
        Optional<Major> updateMajor = meberUpdateReqDto.getMajor();
        Optional<Track> updateTrack = meberUpdateReqDto.getTrack();
        Optional<TechStack> updateTechStack = meberUpdateReqDto.getTechStack();
        Optional<String> updateLikelionEmail = meberUpdateReqDto.getLikelionEmail();
        Optional<String> updateEmail = meberUpdateReqDto.getEmail();
        Optional<Integer> updateTerm = meberUpdateReqDto.getTerm();
        Optional<Position> updatePosition = meberUpdateReqDto.getPosition();
        Optional<String> updateBirth = meberUpdateReqDto.getBirth();
        Optional<String> updateGithub = meberUpdateReqDto.getGithub();
        String updateUpdateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
        Long memberId = member.getId();

        if (updateStudentName != null) {
            jpaMemberDetailRepository.updateStudentName(updateStudentName, memberId);
        }
        if (updatePhoneNumber != null) {
            jpaMemberDetailRepository.updatePhoneNumber(updatePhoneNumber, memberId);
        }
        if (updateDescription != null) {
            jpaMemberDetailRepository.updateDescription(updateDescription, memberId);
        }
        if (updateGrade != null) {
            jpaMemberDetailRepository.updateGrade(updateGrade, memberId);
        }
        if (updateMajor != null) {
            jpaMemberDetailRepository.updateMajor(updateMajor, memberId);
        }
        if (updateTrack != null) {
            jpaMemberDetailRepository.updateTrack(updateTrack, memberId);
        }
        if (updateTechStack != null) {
            jpaMemberDetailRepository.updateTechStack(updateTechStack, memberId);
        }
        if (updateLikelionEmail != null) {
            jpaMemberDetailRepository.updateLikelionEmail(updateLikelionEmail, memberId);
        }
        if (updateEmail != null) {
            jpaMemberDetailRepository.updateEmail(updateEmail, memberId);
        }
        if (updateTerm != null) {
            jpaMemberDetailRepository.updateTerm(updateTerm, memberId);
        }
        if (updatePosition != null) {
            jpaMemberDetailRepository.updatePosition(updatePosition, memberId);
        }
        if (updateBirth != null) {
            jpaMemberDetailRepository.updateBirth(updateBirth, memberId);
        }
        if (updateGithub != null) {
            jpaMemberDetailRepository.updateGithub(updateGithub, memberId);
        }
        jpaMemberDetailRepository.updateUpdateDate(updateUpdateDate, memberId);
        MemberResDto memberResDto = new MemberResDto(member);

        return memberResDto;
    }

    @Transactional
    public void delete(Member member) {
        jpaMemberRepository.deleteByMember(member);
    }
}
