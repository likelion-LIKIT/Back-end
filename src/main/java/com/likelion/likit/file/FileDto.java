package com.likelion.likit.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileDto {
    private String fileName;
    private String filePath;
    private Long fileSize;
    private boolean isThumbnail;

    @Builder
    public FileDto(String fileName, String filePath, Long fileSize, boolean isThumbnail) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.isThumbnail = isThumbnail;
    }
}
