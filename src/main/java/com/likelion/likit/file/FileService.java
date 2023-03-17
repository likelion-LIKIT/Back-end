package com.likelion.likit.file;

import com.likelion.likit.exception.CustomException;
import com.likelion.likit.exception.ExceptionEnum;
import com.likelion.likit.file.dto.FileDto;
import com.likelion.likit.file.dto.FileResDto;
import com.likelion.likit.file.entity.File;
import com.likelion.likit.file.repository.JpaFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileService {
    private final JpaFileRepository jpaFileRepository;

    @Transactional(readOnly = true)
    public FileDto findByFileId(Long id) {
        File file = jpaFileRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.FILENOTEXIST));

        FileDto fileDto = FileDto.builder()
                .fileName(file.getFileName())
                .filePath(file.getFilePath())
                .fileSize(file.getFileSize())
                .isThumbnail(file.isThumbnail())
                .build();

        return fileDto;
    }

    @Transactional(readOnly = true)
    public List<Long> findAllByDiary(Long id, boolean isThumbnail) {
        List<File> fileList = jpaFileRepository.findAllByDiaryIdAndIsThumbnail(id, isThumbnail);
        List<Long> fileId = new ArrayList<>();
        for (File file : fileList) {
            fileId.add(file.getId());
        }
        return fileId;
    }
}
