package com.likelion.likit.activity.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class ActivityFile {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(nullable = false)
    private String fileName;

    private String filePath;

    private Long fileSize;

    private int isDifference;

    @Builder
    public ActivityFile(String fileName, String filePath, Long fileSize, int isDifference) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.isDifference = isDifference;
    }
}
