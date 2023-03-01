package com.likelion.likit.member.entity;

import lombok.Getter;

@Getter
public enum Track {
    PM_DESIGN("PM_DESIGN"),
    FRONT_END("FRONT_END"),
    BACK_END("BACK_END"),
    MOBILE("MOBILE");
    private String track;

    Track(String track) {
        this.track = track;
    }
}
