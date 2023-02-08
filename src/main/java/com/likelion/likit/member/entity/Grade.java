package com.likelion.likit.member.entity;

import lombok.Getter;

@Getter
public enum Grade {
    FIRST("FIRST"),
    SECOND("SECOND"),
    THIRD("THIRD"),
    FOURTH("FOURTH"),
    FIFTH("FIFTH");
    private String grade;

    Grade(String grade) {
        this.grade = grade;
    }
}
