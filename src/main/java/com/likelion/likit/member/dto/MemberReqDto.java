package com.likelion.likit.member.dto;

import com.likelion.likit.member.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberReqDto {
    String studentId;
    String password;
    String studentName;
    String phoneNumber;
    Grade grade;
    Major major;
    Track track;
    String email;
    Integer term;
    Boolean isActive;
    Boolean isStaff;
    Boolean isSuperuser;

    public Member toEntity(String password){
        return Member.builder()
                .studentId(studentId)
                .password(password)
                .isActive(isActive)
                .isStaff(isStaff)
                .isSuperuser(isSuperuser)
                .build();
    }

    public MemberDetail detailEntity(Member member){
        return MemberDetail.builder()
                .member(member)
                .studentName(studentName)
                .phoneNumber(phoneNumber)
                .grade(grade)
                .major(major)
                .track(track)
                .email(email)
                .term(term)
                .build();
    }


}
