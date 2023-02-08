package com.likelion.likit.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class MemberTechStack {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberdetail_id")
    @JsonIgnore
    private MemberDetail memberDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "techstack_id")
    @JsonIgnore
    private TechStack techStack;

    @Builder
    public MemberTechStack(MemberDetail memberDetail, TechStack techStack) {
        this.memberDetail = memberDetail;
        this.techStack = techStack;
    }

}
