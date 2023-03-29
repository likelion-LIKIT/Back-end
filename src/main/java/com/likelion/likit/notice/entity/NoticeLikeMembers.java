package com.likelion.likit.notice.entity;

import com.likelion.likit.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class NoticeLikeMembers {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Notice notice;

    @Builder
    public NoticeLikeMembers(Member member, Notice notice) {
        this.member = member;
        this.notice = notice;
    }
}
