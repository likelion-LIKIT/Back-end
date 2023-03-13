package com.likelion.likit.diary.dto;

import com.likelion.likit.diary.entity.Category;
import com.likelion.likit.diary.entity.Diary;
import com.likelion.likit.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class DiaryReqDto {
    private String title;
    private String description;
    private String location;
    private Category category;

    @Builder
    public DiaryReqDto(Diary diary) {
        this.title = diary.getTitle();
        this.description = diary.getDescription();
        this.location = diary.getLocation();
        this.category = diary.getCategory();
    }

    public Diary toEntity(Member member) {
        return Diary.builder()
                .member(member)
                .title(title)
                .description(description)
                .location(location)
                .category(category)
                .build();
    }
}
