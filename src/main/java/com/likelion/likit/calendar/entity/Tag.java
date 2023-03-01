package com.likelion.likit.calendar.entity;

import lombok.Getter;

@Getter
public enum Tag {
    ALL("ALL"),
    PM_DESIGN("PM_DESIGN"),
    FRONT_END("FRONTEND"),
    BACK_END("BACK_END"),
    HACKATHON("HACKATHON"),
    MEETING("MEETING"),
    ETC("ETC");

    private String tag;

    Tag(String tag) {
        this.tag = tag;
    }
}
