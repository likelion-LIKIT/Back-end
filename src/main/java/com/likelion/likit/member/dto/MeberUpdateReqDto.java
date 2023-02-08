package com.likelion.likit.member.dto;

import com.likelion.likit.member.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class MeberUpdateReqDto {
    private Optional<String> studentName;
    private Optional<String> phoneNumber;
    private Optional<String> description;
    private Optional<Grade> grade;
    private Optional<Major> major;
    private Optional<Track> track;
    private Optional<TechStack> techStack;
    private Optional<String> likelionEmail;
    private Optional<String> email;
    private Optional<Integer> term;
    private Optional<Position> position;
    private Optional<String> birth;
    private Optional<String> github;


    public MeberUpdateReqDto(MemberDetail memberDetail) {
        this.studentName = Optional.ofNullable(memberDetail.getStudentName());
        this.phoneNumber = Optional.ofNullable(memberDetail.getPhoneNumber());
        this.description = Optional.ofNullable(memberDetail.getDescription());
        this.grade = Optional.ofNullable(memberDetail.getGrade());
        this.major = Optional.ofNullable(memberDetail.getMajor());
        this.track = Optional.ofNullable(memberDetail.getTrack());
        this.techStack = Optional.ofNullable(memberDetail.getTechStack());
        this.likelionEmail = Optional.ofNullable(memberDetail.getLikelionEmail());
        this.email = Optional.ofNullable(memberDetail.getEmail());
        this.term = Optional.ofNullable(memberDetail.getTerm());
        this.position = Optional.ofNullable(memberDetail.getPosition());
        this.birth = Optional.ofNullable(memberDetail.getBirth());
        this.github = Optional.ofNullable(memberDetail.getGithub());
    }
}
