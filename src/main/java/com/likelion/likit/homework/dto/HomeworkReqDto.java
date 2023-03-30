package com.likelion.likit.homework.dto;

import com.likelion.likit.homework.entity.Category;
import com.likelion.likit.homework.entity.Homework;
import com.likelion.likit.homework.entity.HomeworkFile;
import com.likelion.likit.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HomeworkReqDto {
    private String title;
    private String description;
    private String location;
    private Category category;
    private String date;
    private boolean temp;

    @Builder
    public HomeworkReqDto(Homework homework) {
        this.title = homework.getTitle();
        this.description = homework.getDescription();
        this.location = homework.getLocation();
        this.category = homework.getCategory();
        this.date = homework.getDate();
        this.temp = homework.isTemp();
    }

    public Homework toEntity(Member member) {
        return Homework.builder()
                .member(member)
                .title(title)
                .description(description)
                .location(location)
                .category(category)
                .date(date)
                .temp(temp)
                .build();
    }
}
