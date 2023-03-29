package com.likelion.likit.file;

import com.likelion.likit.exception.CustomException;
import com.likelion.likit.exception.ExceptionEnum;
import com.likelion.likit.file.entity.ImageFile;
import com.likelion.likit.file.repository.JpaImageFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    @Value("${part4.upload.path}")
    private String uploadPath;

    private final JpaImageFileRepository jpaImageFileRepository;

    @Transactional
    public ImageFile parseImage(List<MultipartFile> multipartFiles, boolean isThumbnail, String studentID, String fileType) throws Exception {
        ImageFile imageFile = null;

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            String path = "image" + java.io.File.separator + studentID + java.io.File.separator + fileType + java.io.File.separator + currentDate;
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


                imageFile = new ImageFile(
                        fileDto.getFileName(),
                        fileDto.getFilePath(),
                        fileDto.getFileSize()
                );

                jpaImageFileRepository.save(imageFile);


                String saveName = uploadPath + File.separator + path + File.separator + saveFileName;

                Path savedPath = Paths.get(saveName);
                multipartFile.transferTo(savedPath);

                file.setWritable(true);
                file.setReadable(true);
            }
        }
        if (imageFile == null) {
            throw new CustomException(ExceptionEnum.FILENOTEXIST);
        }
       return imageFile;
    }
}
