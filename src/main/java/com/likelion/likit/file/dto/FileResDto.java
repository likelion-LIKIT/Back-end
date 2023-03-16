package com.likelion.likit.file.dto;

import com.likelion.likit.file.entity.File;
import lombok.Getter;

@Getter
public class FileResDto {
    private Long fileId;

    public FileResDto(File entity) {
        this.fileId = entity.getId();
    }
}
