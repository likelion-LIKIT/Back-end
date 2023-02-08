package com.likelion.likit.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class TechStack {

    @Id
    @GeneratedValue
    private Long id;
    private String techStack;
    @OneToMany(mappedBy = "techstack")
    private List<MemberDetail> memberDetails = new ArrayList<>();

    public TechStack(String techStack) {
        this.techStack = techStack;
    }

    public void addMemberDetail(MemberDetail memberDetail) {
        this.memberDetails.add(memberDetail);
    }
}

