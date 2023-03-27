package com.likelion.likit.member;

import com.likelion.likit.exception.CustomException;
import com.likelion.likit.exception.ExceptionEnum;
import com.likelion.likit.member.dto.MemberUpdateReqDto;
import com.likelion.likit.member.dto.MemberResDto;
import com.likelion.likit.member.dto.TechUpdateDto;
import com.likelion.likit.member.entity.*;
import com.likelion.likit.member.repository.JpaMemberDetailRepository;
import com.likelion.likit.member.repository.JpaMemberRepository;
import com.likelion.likit.member.repository.JpaMemberTechStackRepository;
import com.likelion.likit.member.repository.JpaTechStackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService  {
    private final JpaMemberRepository jpaMemberRepository;
    private final JpaMemberDetailRepository jpaMemberDetailRepository;
    private final JpaTechStackRepository jpaTechStackRepository;
    private final JpaMemberTechStackRepository jpaMemberTechStackRepository;

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
    public MemberResDto update(Member member, MemberUpdateReqDto memberUpdateReqDto, TechUpdateDto techUpdateDto) {

        String updateStudentName = memberUpdateReqDto.getStudentName();
        String updatePhoneNumber = memberUpdateReqDto.getPhoneNumber();
        String updateDescription = memberUpdateReqDto.getDescription();
        Grade updateGrade = memberUpdateReqDto.getGrade();
        Major updateMajor = memberUpdateReqDto.getMajor();
        Track updateTrack = memberUpdateReqDto.getTrack();
        String updateLikelionEmail = memberUpdateReqDto.getLikelionEmail();
        String updateEmail = memberUpdateReqDto.getEmail();
        Integer updateTerm = memberUpdateReqDto.getTerm();
        Position updatePosition = memberUpdateReqDto.getPosition();
        String updateBirth = memberUpdateReqDto.getBirth();
        String updateGithub = memberUpdateReqDto.getGithub();
        String updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
        Long memberId = member.getId();
        MemberDetail memberDetails = jpaMemberDetailRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ExceptionEnum.StudentIdNotMatched));;
        Long id = memberDetails.getId();

        List<String> tech = techUpdateDto.getTech();
        if (tech != null) {
            List<MemberTechStack> memberTechStacks = jpaMemberTechStackRepository.findAllByMemberDetail(memberDetails);
            if (!memberTechStacks.isEmpty()){
                List<Long> ids = new ArrayList<>();
                for (MemberTechStack memberTechStack : memberTechStacks) {
                    ids.add(memberTechStack.getId());
                }
                jpaMemberTechStackRepository.deleteAllByIdInQuery(ids);
            }
            List<MemberTechStack> newMemberTechStacks = new ArrayList<>();
            for(String ts : tech) {
                TechStack techStack = jpaTechStackRepository.findByTechStack(ts);
                MemberTechStack memberTechStack
                        = MemberTechStack.builder()
                        .memberDetail(memberDetails)
                        .techStack(techStack)
                        .build();
                newMemberTechStacks.add(memberTechStack);
            }
            jpaMemberTechStackRepository.saveAllAndFlush(newMemberTechStacks);
        }

        if (updateStudentName != null) {
            jpaMemberDetailRepository.updateStudentName(updateStudentName, id);
        }
        if (updatePhoneNumber != null) {
            jpaMemberDetailRepository.updatePhoneNumber(updatePhoneNumber, id);
        }
        if (updateDescription != null) {
            jpaMemberDetailRepository.updateDescription(updateDescription, id);
        }
        if (updateGrade != null) {
            jpaMemberDetailRepository.updateGrade(updateGrade, id);
        }
        if (updateMajor != null) {
            jpaMemberDetailRepository.updateMajor(updateMajor, id);
        }
        if (updateTrack != null) {
            jpaMemberDetailRepository.updateTrack(updateTrack, id);
        }
        if (updateLikelionEmail != null) {
            jpaMemberDetailRepository.updateLikelionEmail(updateLikelionEmail, memberId);
        }
        if (updateEmail != null) {
            jpaMemberDetailRepository.updateEmail(updateEmail, id);
        }
        if (updateTerm != null) {
            jpaMemberDetailRepository.updateTerm(updateTerm, id);
        }
        if (updatePosition != null) {
            jpaMemberDetailRepository.updatePosition(updatePosition, id);
        }
        if (updateBirth != null) {
            jpaMemberDetailRepository.updateBirth(updateBirth, id);
        }
        if (updateGithub != null) {
            jpaMemberDetailRepository.updateGithub(updateGithub, id);
        }
        jpaMemberDetailRepository.updateDate(updateDate, id);
        MemberResDto memberResDto = new MemberResDto(member);

        return memberResDto;
    }



    @Transactional
    public void delete(Member member) {
        jpaMemberRepository.delete(member);
    }

    public MemberResDto findMember(String studentId) {
        Member member = jpaMemberRepository.findByStudentId(studentId).orElseThrow(() -> new CustomException(ExceptionEnum.StudentIdNotMatched));
        return new MemberResDto(member);
    }
}
