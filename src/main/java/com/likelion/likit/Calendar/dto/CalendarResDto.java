package com.likelion.likit.Calendar.dto;

import com.likelion.likit.Calendar.entity.Tag;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CalendarResDto {
    List<CalendarDto> calendarDtoList;

    public CalendarResDto() {
        this.calendarDtoList = calendarDtoList;
    }

    @Getter
    private class CalendarDto {
        private Long id;
        private String description;
        private Tag tag;
        private LocalDate date;
        private LocalDateTime time;

        public CalendarDto(Long id, String description, Tag tag, LocalDate date, LocalDateTime time) {
            this.id = id;
            this.description = description;
            this.tag = tag;
            this.date = date;
            this.time = time;
        }
    }
}
