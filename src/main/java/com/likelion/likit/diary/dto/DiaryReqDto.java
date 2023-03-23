package com.likelion.likit.diary.dto;

import com.likelion.likit.diary.entity.Category;
import com.likelion.likit.diary.entity.Diary;
import com.likelion.likit.diary.entity.DiaryFile;
import com.likelion.likit.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryReqDto {
    private String title;
    private String description;
    private String location;
    private Category category;
    private String date;

    @Builder
    public DiaryReqDto(Diary diary) {
        this.title = diary.getTitle();
        this.description = diary.getDescription();
        this.location = diary.getLocation();
        this.category = diary.getCategory();
        this.date = diary.getDate();
    }

    public Diary toEntity(Member member, DiaryFile thumbnail) {
        return Diary.builder()
                .member(member)
                .thumbnail(thumbnail)
                .title(title)
                .description(description)
                .location(location)
                .category(category)
                .date(date)
                .build();
    }
}
