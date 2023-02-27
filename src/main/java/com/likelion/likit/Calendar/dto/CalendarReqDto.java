package com.likelion.likit.Calendar.dto;

import com.likelion.likit.Calendar.entity.Calendar;
import com.likelion.likit.Calendar.entity.Tag;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class CalendarReqDto {
    private String description;
    private Tag tag;
    private LocalDateTime dateTime;

    public Calendar toEntity() {
        return Calendar.builder()
                .description(description)
                .tag(tag)
                .dateTime(dateTime)
                .build();
    }

}
