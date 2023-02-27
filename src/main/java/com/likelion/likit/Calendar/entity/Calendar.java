package com.likelion.likit.Calendar.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class Calendar {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private Tag tag;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalDateTime time;

    @Builder
    public Calendar(String description, Tag tag, LocalDate date, LocalDateTime time) {
        this.description = description;
        this.tag = tag;
        this.date = date;
        this.time = time;
    }
}
