package com.likelion.likit.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class MemberDetail {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
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

    @OneToMany(mappedBy = "memberDetail", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<MemberTechStack> memberTechStacks = new HashSet<>();

    @Column(name = "likelion_email", unique = true)
    private String likelionEmail;

    @Column(unique = true)
    private String email;

    private Integer term;

    @OneToMany(mappedBy = "memberDetail", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Position> positions = new HashSet<>();

    private String birth;
    private String github;
    private String updateDate;

    @Builder
    public MemberDetail(Member member, String studentName, String phoneNumber, String description,
                        Grade grade, Major major, Track track, Set<MemberTechStack> memberTechStacks, String likelionEmail,
                        String email, Integer term, Set<Position> positions, String birth, String github) {
        this.member = member;
        this.studentName = studentName;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.grade = grade;
        this.major = major;
        this.track = track;
        this.memberTechStacks = memberTechStacks;
        this.likelionEmail = likelionEmail;
        this.email = email;
        this.term = term;
        this.positions = positions;
        this.birth = birth;
        this.github = github;
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
    }

    public void addTechStack(MemberTechStack memberTechStack) {
        this.memberTechStacks.add(memberTechStack);
    }
}
