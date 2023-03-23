package com.likelion.likit.diary;

import com.likelion.likit.diary.repository.JpaDiaryFileRepository;
import com.likelion.likit.file.FileDto;
import com.likelion.likit.diary.entity.DiaryFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component

public class DiaryFileHandler {

    private final JpaDiaryFileRepository jpaDiaryFileRepository;

    @Value("${part4.upload.path}")
    private String uploadPath;

    public DiaryFileHandler(JpaDiaryFileRepository jpaDiaryFileRepository) {
        this.jpaDiaryFileRepository = jpaDiaryFileRepository;
    }

    public List<DiaryFile> parseFile(List<MultipartFile> multipartFiles, boolean isThumbnail) throws Exception {
        List<DiaryFile> diaryFiles = new ArrayList<>();

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            String path = "file" + java.io.File.separator + "diary" + java.io.File.separator + currentDate;
            String allPath = uploadPath + path;
            java.io.File file = new java.io.File(allPath);
            file.mkdirs();

            for (MultipartFile multipartFile : multipartFiles) {
                String originFileName = multipartFile.getOriginalFilename();
                String fileExtension;
                String contentType = multipartFile.getContentType();

                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    fileExtension = "." + (originFileName != null ? originFileName.split("\\.") : new String[0])[1];
                }

                String saveFileName = System.nanoTime() + fileExtension;


                FileDto fileDto = FileDto.builder()
                        .fileName(originFileName)
                        .filePath(path + java.io.File.separator + saveFileName)
                        .fileSize(multipartFile.getSize())
                        .isThumbnail(isThumbnail)
                        .build();


                DiaryFile diaryFile1 = new DiaryFile(
                        fileDto.getFileName(),
                        fileDto.getFilePath(),
                        fileDto.getFileSize(),
                        fileDto.isThumbnail()
                );

                diaryFiles.add(diaryFile1);

                String saveName = uploadPath + File.separator + path + File.separator + saveFileName;

                Path savedPath = Paths.get(saveName);
                multipartFile.transferTo(savedPath);

                file.setWritable(true);
                file.setReadable(true);
            }
        }
        return diaryFiles;
    }


    public Resource download(String filePath) throws IOException {
        String file = uploadPath + filePath;
        Path path = Paths.get(file);
        return new InputStreamResource(Files.newInputStream(path));
    }
}
