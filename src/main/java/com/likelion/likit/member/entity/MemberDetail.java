package com.likelion.likit.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Entity
@NoArgsConstructor
public class MemberDetail {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") @JsonIgnore
    private Member member;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(columnDefinition = "LONGTEXT", nullable = true)
    private String description;

    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    @Enumerated(value = EnumType.STRING)
    private Major major;

    @Enumerated(value = EnumType.STRING)
    private Track track;

    @Enumerated(value = EnumType.STRING)
    private TechStack techStack;

    @Column(name = "likelion_email", unique = true)
    private String likelionEmail;

    @Column(unique = true)
    private String email;

    private Integer term;
    private Position position;
    private String birth;
    private String github;
    private String updateDate;

    @Builder
    public MemberDetail(Member member, String studentName, String phoneNumber, String description,
                        Grade grade, Major major, Track track, TechStack techStack, String likelionEmail,
                        String email, Integer term, Position position, String birth, String github) {
        this.member = member;
        this.studentName = studentName;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.grade = grade;
        this.major = major;
        this.track = track;
        this.techStack = techStack;
        this.likelionEmail = likelionEmail;
        this.email = email;
        this.term = term;
        this.position = position;
        this.birth = birth;
        this.github = github;
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
    }
}
