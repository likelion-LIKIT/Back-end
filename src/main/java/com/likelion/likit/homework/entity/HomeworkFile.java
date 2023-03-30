package com.likelion.likit.homework.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class HomeworkFile {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "homework_id")
    private Homework homework;

    @Column(nullable = false)
    private String fileName;

    private String filePath;

    private Long fileSize;


    @Builder
    public HomeworkFile(String fileName, String filePath, Long fileSize) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
