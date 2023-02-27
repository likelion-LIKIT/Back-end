package com.likelion.likit.Calendar.dto;

import com.likelion.likit.Calendar.entity.Calendar;
import com.likelion.likit.Calendar.entity.Tag;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
public class CalendarReqDto {
    private String description;
    private Tag tag;
    private LocalDate date;
    private LocalDateTime time;

    public Calendar toEntity() {
        return Calendar.builder()
                .description(description)
                .tag(tag)
                .date(date)
                .time(time)
                .build();
    }

}
