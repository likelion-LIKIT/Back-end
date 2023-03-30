package com.likelion.likit.ledger;

import com.likelion.likit.ledger.entity.LedgerFile;
import com.likelion.likit.ledger.repository.JpaLedgerFileRepository;
import com.likelion.likit.file.FileDto;
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

public class LedgerFileHandler {

    private final JpaLedgerFileRepository jpaLedgerFileRepository;

    @Value("${part4.upload.path}")
    private String uploadPath;

    public LedgerFileHandler(JpaLedgerFileRepository jpaLedgerFileRepository) {
        this.jpaLedgerFileRepository = jpaLedgerFileRepository;
    }

    public List<LedgerFile> parseFile(List<MultipartFile> multipartFiles, String studentID) throws Exception {
        List<LedgerFile> ledgerFiles = new ArrayList<>();

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            String path = "file" + java.io.File.separator + studentID + java.io.File.separator + "ledger" + java.io.File.separator + currentDate;
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
                        .isThumbnail(false)
                        .build();


                LedgerFile ledgerFile1 = new LedgerFile(
                        fileDto.getFileName(),
                        fileDto.getFilePath(),
                        fileDto.getFileSize()
                );

                ledgerFiles.add(ledgerFile1);

                String saveName = uploadPath + File.separator + path + File.separator + saveFileName;

                Path savedPath = Paths.get(saveName);
                multipartFile.transferTo(savedPath);

                file.setWritable(true);
                file.setReadable(true);
            }
        }
        return ledgerFiles;
    }


    public Resource download(String filePath) throws IOException {
        String file = uploadPath + filePath;
        Path path = Paths.get(file);
        return new InputStreamResource(Files.newInputStream(path));
    }
}
