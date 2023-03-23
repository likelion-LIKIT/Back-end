package com.likelion.likit.notice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class NoticeFile {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @Column(nullable = false)
    private String fileName;

    private String filePath;

    private Long fileSize;

    private boolean isThumbnail;

    @Builder
    public NoticeFile(String fileName, String filePath, Long fileSize, boolean isThumbnail) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.isThumbnail = isThumbnail;
    }
}
