package com.likelion.likit.notice.entity;

import lombok.Getter;

@Getter
public enum Category {
    해커톤("해커톤"),
    스터디("스터디"),
    동아리("동아리"),
    중앙("중앙");

    private String category;

    Category(String category) {
        this.category = category;
    }
}
