package com.likelion.likit.member.entity;

import lombok.Getter;

@Getter
public enum Category {
    대표("REPRESENTATIVE"),
    부대표("DEPUTY_REPRESENTATIVE"),
    트랙장("TRACK_LEADER"),
    운영진("MANAGEMENT"),
    아기사자("BABY_LION");
    private String category;

    Category(String category) {
        this.category = category;
    }
}
