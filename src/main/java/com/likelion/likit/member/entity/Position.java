package com.likelion.likit.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Position {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "memberdetail_id")
    @JsonIgnore
    private MemberDetail memberDetail;

    @Builder
    public Position(Category category, MemberDetail memberDetail) {
        this.category = category;
        this.memberDetail = memberDetail;
    }
}
