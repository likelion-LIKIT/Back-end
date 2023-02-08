package com.likelion.likit.member.entity;

import lombok.Getter;

@Getter
public enum Position {
    REPRESENTATIVE("REPRESENTATIVE"),
    DEPUTY_REPRESENTATIVE("DEPUTY_REPRESENTATIVE"),
    TRACK_LEADER("TRACK_LEADER"),
    MANAGEMENT("MANAGEMENT"),
    BABY_LION("BABY_LION");
    private String position;

    Position(String position) {
        this.position = position;
    }
}
