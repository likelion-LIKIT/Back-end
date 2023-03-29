package com.likelion.likit.file.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class ImageFile {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String fileName;

    private String filePath;

    private Long fileSize;

    @Builder
    public ImageFile(String fileName, String filePath, Long fileSize) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
