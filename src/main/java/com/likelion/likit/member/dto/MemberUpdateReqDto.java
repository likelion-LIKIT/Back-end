package com.likelion.likit.member.dto;

import com.likelion.likit.member.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class MemberUpdateReqDto {
    private String studentName;
    private String phoneNumber;
    private String description;
    private Grade grade;
    private Major major;
    private Track track;
    private String likelionEmail;
    private String email;
    private Integer term;
    private Position position;
    private String birth;
    private String github;


    public MemberUpdateReqDto(MemberDetail memberDetail) {
        this.studentName = memberDetail.getStudentName();
        this.phoneNumber = memberDetail.getPhoneNumber();
        this.description = memberDetail.getDescription();
        this.grade = memberDetail.getGrade();
        this.major = memberDetail.getMajor();
        this.track = memberDetail.getTrack();
        this.likelionEmail = memberDetail.getLikelionEmail();
        this.email = memberDetail.getEmail();
        this.term = memberDetail.getTerm();
        this.position = memberDetail.getPosition();
        this.birth = memberDetail.getBirth();
        this.github = memberDetail.getGithub();
    }
}
