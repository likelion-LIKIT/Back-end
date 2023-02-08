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

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService  {
    private final JpaMemberRepository jpaMemberRepository;
    private final JpaMemberDetailRepository jpaMemberDetailRepository;

    public Member findByStudentId(String studentId) {
        Member member = jpaMemberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.StudentIdNotMatched));
        return member;
    }

    public void join(Member member) {
        jpaMemberRepository.save(member);
    }

    public void saveDetail(MemberDetail memberDetail) {
        jpaMemberDetailRepository.save(memberDetail);
    }


    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {
        return jpaMemberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.StudentIdNotMatched));
    }

//    public MemberResDto update(Member member, MeberUpdateReqDto meberUpdateReqDto) {
//
//        Optional<String> updateStudentName = meberUpdateReqDto.getStudentName();
//        Optional<String> updatePhoneNumber = meberUpdateReqDto.getPhoneNumber();
//        Optional<String> updateDescription = meberUpdateReqDto.getDescription();
//        Optional<Grade> updateGrade = meberUpdateReqDto.getGrade();
//        Optional<Major> updateMajor = meberUpdateReqDto.getMajor();
//        Optional<Track> updateTrack = meberUpdateReqDto.getTrack();
//        Optional<TechStack> updateTechStack = meberUpdateReqDto.getTechStack();
//        Optional<String> updateLikelionEmail = meberUpdateReqDto.getLikelionEmail();
//        Optional<String> updateEmail = meberUpdateReqDto.getEmail();
//        Optional<Integer> updateTerm = meberUpdateReqDto.getTerm();
//        Optional<Position> updatePosition = meberUpdateReqDto.getPosition();
//        Optional<String> updateBirth = meberUpdateReqDto.getBirth();
//        Optional<String> updateGithub = meberUpdateReqDto.getGithub();
//
//        Long memberId = member.getId();
//
//    }
}
