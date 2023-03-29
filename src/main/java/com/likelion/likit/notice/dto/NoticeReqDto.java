package com.likelion.likit.notice.dto;

import com.likelion.likit.notice.entity.Category;
import com.likelion.likit.notice.entity.Notice;
import com.likelion.likit.notice.entity.NoticeFile;
import com.likelion.likit.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeReqDto {
    private String title;
    private String description;
    private String location;
    private Category category;
    private String date;
    private boolean temp;

    @Builder
    public NoticeReqDto(Notice notice) {
        this.title = notice.getTitle();
        this.description = notice.getDescription();
        this.location = notice.getLocation();
        this.category = notice.getCategory();
        this.date = notice.getDate();
        this.temp = notice.isTemp();
    }

    public Notice toEntity(Member member, NoticeFile thumbnail) {
        return Notice.builder()
                .member(member)
                .thumbnail(thumbnail)
                .title(title)
                .description(description)
                .location(location)
                .category(category)
                .date(date)
                .temp(temp)
                .build();
    }
}
