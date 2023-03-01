package com.likelion.likit.calendar.dto;

import com.likelion.likit.calendar.entity.Calendar;
import com.likelion.likit.calendar.entity.Tag;
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
