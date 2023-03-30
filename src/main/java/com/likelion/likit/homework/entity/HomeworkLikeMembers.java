package com.likelion.likit.homework.entity;

import com.likelion.likit.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class HomeworkLikeMembers {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Homework homework;

    @Builder
    public HomeworkLikeMembers(Member member, Homework homework) {
        this.member = member;
        this.homework = homework;
    }
}
