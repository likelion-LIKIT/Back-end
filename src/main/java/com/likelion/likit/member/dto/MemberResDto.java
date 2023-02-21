package com.likelion.likit.member.dto;

import com.likelion.likit.member.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberResDto {
    private String studentId;
    private StudentDto memberDetail;

    public MemberResDto(Member member) {
        this.studentId = member.getStudentId();
        this.memberDetail = new StudentDto(member.getMemberDetails());
    }

    @Getter
    private class StudentDto {
        private String studentName;
        private String phoneNumber;
        private String description;
        private Grade grade;
        private Major major;
        private Track track;
        private MemberTechStackDto techStack;
        private String likelionEmail;
        private String email;
        private Integer term;
        private Position position;
        private String birth;
        private String github;

        public StudentDto(MemberDetail memberDetail) {
            this.studentName = memberDetail.getStudentName();
            this.phoneNumber = memberDetail.getPhoneNumber();
            this.description = memberDetail.getDescription();
            this.grade = memberDetail.getGrade();
            this.major = memberDetail.getMajor();
            this.track = memberDetail.getTrack();
            this.techStack = new MemberTechStackDto(memberDetail.getMemberTechStacks());
            this.likelionEmail = memberDetail.getLikelionEmail();
            this.email = memberDetail.getEmail();
            this.term = memberDetail.getTerm();
            this.position = memberDetail.getPosition();
            this.birth = memberDetail.getBirth();
            this.github = memberDetail.getGithub();
        }
    }

    @Getter
    private class MemberTechStackDto {
        private List<String> techStack;

        public MemberTechStackDto(List<MemberTechStack> memberTechStack) {
            List<String> TechStackInfo = new ArrayList<>();
            for (MemberTechStack memberTechStack1 : memberTechStack) {
                TechStackDto techStackDto = new TechStackDto(memberTechStack1.getTechStack());
                TechStackInfo.add(techStackDto.techStackName);
            }
            this.techStack = TechStackInfo;

        }

        }

    @Getter
    private class TechStackDto {
        private String techStackName;

        public TechStackDto(TechStack techStack) {
            this.techStackName = techStack.getTechStack();
        }
    }
}


