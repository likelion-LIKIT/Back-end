package com.likelion.likit.file;

import com.likelion.likit.file.dto.FileDto;
import com.likelion.likit.file.entity.File;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandler {

    public List<File> parseFile(List<MultipartFile> multipartFiles, boolean isThumbnail) throws Exception {
        List<File> files = new ArrayList<>();

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String absolutePath = new java.io.File("").getAbsolutePath() + java.io.File.separator + java.io.File.separator;
            String path = "file" + java.io.File.separator + currentDate;
            java.io.File file = new java.io.File(path);

            if (!file.exists()) {
                boolean madeFile = file.mkdirs();

                if (!madeFile) {
                    System.out.printf("%s is wrong", path);
                }

            }

            for (MultipartFile multipartFile : multipartFiles) {
                String fileExtension;
                String contentType = multipartFile.getContentType();

                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if (contentType.contains("image/jpeg")) fileExtension = ".jpg";
                    else if (contentType.contains("image/jpg")) fileExtension = ".jpg";
                    else if (contentType.contains("image/png")) fileExtension = ".png";
                    else if (contentType.contains("application/pdf")) fileExtension = ".pdf";
                    else break;
                }

                String saveFileName = System.nanoTime() + fileExtension;


                FileDto fileDto = FileDto.builder()
                        .fileName(multipartFile.getOriginalFilename())
                        .filePath(path + java.io.File.separator + saveFileName)
                        .fileSize(multipartFile.getSize())
                        .isThumbnail(isThumbnail)
                        .build();


                File file1 = new File(
                        fileDto.getFileName(),
                        fileDto.getFilePath(),
                        fileDto.getFileSize(),
                        fileDto.isThumbnail()
                );

                files.add(file1);

                file = new java.io.File(absolutePath + path + java.io.File.separator + saveFileName);
                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);
            }
        }
        return files;
    }
}