package com.likelion.likit.activity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.likit.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE} , orphanRemoval = true)
    private ActivityFile icon;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "activity_start_date")
    private String startDate;

    @Column(name = "activity_end_date")
    private String endDate;

    @Column(name = "creation_date") @CreatedDate
    private String creationDate;

    @Column(name = "update_date") @CreatedDate
    private String updateDate;

    private String achieve;

    private String github;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "activity", cascade = {CascadeType.PERSIST, CascadeType.REMOVE} , orphanRemoval = true)
    private List<ActivityURL> activityURLs = new ArrayList<>();

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE} , orphanRemoval = true)
    private ActivityFile serviceThumbnail;

    @JsonIgnore
    @OneToMany(mappedBy = "activity", cascade = {CascadeType.PERSIST, CascadeType.REMOVE} , orphanRemoval = true)
    private List<ActivityFile> activityFiles = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
    private List<ProjectJoin> projectJoins = new ArrayList<>();

    @Builder
    public Activity(ActivityFile icon, String title, String description, String startDate, String endDate,
                    String achieve, String github, List<ActivityURL> activityURLs, ActivityFile serviceThumbnail,
                    List<ActivityFile> activityFiles, List<ProjectJoin> projectJoins, Member member) {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.member = member;
        this.startDate = String.format(startDate, DateTimeFormatter.ofPattern("yyy.MM.dd"));
        this.endDate = String.format(endDate, DateTimeFormatter.ofPattern("yyy.MM.dd"));
        this.creationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
        this.achieve = achieve;
        this.github = github;
        this.activityURLs = activityURLs;
        this.serviceThumbnail = serviceThumbnail;
        this.activityFiles = activityFiles;
        this.projectJoins = projectJoins;
    }
}
