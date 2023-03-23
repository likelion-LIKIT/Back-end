package com.likelion.likit.notice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Notice {

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
    @OneToMany(mappedBy = "notice", cascade = {CascadeType.PERSIST, CascadeType.REMOVE} , orphanRemoval = true)
    private List<NoticeFile> noticeFiles = new ArrayList<>();

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE} , orphanRemoval = true)
    private NoticeFile thumbnail;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY)
    private List<NoticeLikeMembers> noticeLikeMembers = new ArrayList<>();

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
    public Notice(String title, String description, String location, Member member, List<NoticeFile> noticeFiles, NoticeFile thumbnail, Category category,
                  List<NoticeLikeMembers> noticeLikeMembers, Integer likes, int visit, String date) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.member = member;
        this.noticeFiles = noticeFiles;
        this.thumbnail = thumbnail;
        this.category = category;
        this.noticeLikeMembers = noticeLikeMembers;
        this.likes = likes;
        this.visit = visit;
        this.date = String.format(date, DateTimeFormatter.ofPattern("yyy.MM.dd"));
        this.creationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
    }
}
