package com.likelion.likit.ledger.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class LedgerFile {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ledger")
    private Ledger ledger;

    @Column(nullable = false)
    private String fileName;

    private String filePath;

    private Long fileSize;


    @Builder
    public LedgerFile(String fileName, String filePath, Long fileSize) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
