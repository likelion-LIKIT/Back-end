package com.likelion.likit.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
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

    @OneToMany(mappedBy = "techStack") @JsonIgnore
    private List<MemberTechStack> memberTechStacks = new ArrayList<>();

    @Builder
    public TechStack(String techStack) {
        this.techStack = techStack;
    }

    public void addMemberDetail(MemberTechStack memberTechStack) {
        this.memberTechStacks.add(memberTechStack);
    }
}

