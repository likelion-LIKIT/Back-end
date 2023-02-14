package com.likelion.likit.member.dto;

import com.likelion.likit.member.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

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
        private List<MemberTechStack> techStack;
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
            this.techStack = memberDetail.getMemberTechStacks();
            this.likelionEmail = memberDetail.getLikelionEmail();
            this.email = memberDetail.getEmail();
            this.term = memberDetail.getTerm();
            this.position = memberDetail.getPosition();
            this.birth = memberDetail.getBirth();
            this.github = memberDetail.getGithub();
        }
    }
}
