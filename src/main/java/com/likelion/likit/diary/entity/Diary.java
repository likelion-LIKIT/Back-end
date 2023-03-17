package com.likelion.likit.diary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.likit.file.entity.File;
import com.likelion.likit.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Diary {

    @PrePersist
    public void PrePersist() {
        this.likes = this.likes == null ? 0 : this.likes;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String location;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "diary", cascade = {CascadeType.PERSIST, CascadeType.REMOVE} , orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE} , orphanRemoval = true)
    private File thumbnail;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private Integer likes;

    @ColumnDefault("0")
    private int visit;

    @Column(name = "date")
    private String date;

    @Column(name = "creation_date") @CreatedDate
    private String creationDate;

    @Column(name = "update_date") @CreatedDate
    private String updateDate;

    @Builder
    public Diary(String title, String description, String location, Member member, List<File> files, File thumbnail, Category category,
                 Integer likes, int visit, String date) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.member = member;
        this.files = files;
        this.thumbnail = thumbnail;
        this.category = category;
        this.likes = likes;
        this.visit = visit;
        this.date = String.format(date, DateTimeFormatter.ofPattern("yyy.MM.dd"));
        this.creationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
    }
}
