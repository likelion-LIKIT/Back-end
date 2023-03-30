package com.likelion.likit.homework.entity;

import lombok.Getter;

@Getter
public enum Category {
    ALL("ALL"),
    PM_DESIGN("PM_DESIGN"),
    FRONT_END("FRONT_END"),
    BACK_END("BACK_END"),
    HACKATHON("HACKATHON");

    private String category;

    Category(String category) {
        this.category = category;
    }
}
