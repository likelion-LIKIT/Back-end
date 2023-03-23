package com.likelion.likit.diary.entity;

import com.likelion.likit.diary.entity.Diary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class DiaryFile {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Column(nullable = false)
    private String fileName;

    private String filePath;

    private Long fileSize;

    private boolean isThumbnail;

    @Builder
    public DiaryFile(String fileName, String filePath, Long fileSize, boolean isThumbnail) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.isThumbnail = isThumbnail;
    }
}
