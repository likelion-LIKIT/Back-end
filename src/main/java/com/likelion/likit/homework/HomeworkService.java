package com.likelion.likit.homework;

import com.likelion.likit.homework.dto.HomeworkReqDto;
import com.likelion.likit.homework.dto.HomeworkResDto;
import com.likelion.likit.homework.entity.Category;
import com.likelion.likit.homework.entity.Homework;
import com.likelion.likit.homework.entity.HomeworkFile;
import com.likelion.likit.homework.entity.HomeworkLikeMembers;
import com.likelion.likit.homework.repository.JpaHomeworkRepository;
import com.likelion.likit.homework.repository.JpaHomeworkLikeRepository;
import com.likelion.likit.exception.CustomException;
import com.likelion.likit.exception.ExceptionEnum;
import com.likelion.likit.homework.repository.JpaHomeworkFileRepository;
import com.likelion.likit.file.FileService;
import com.likelion.likit.file.entity.ImageFile;
import com.likelion.likit.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeworkService {

    @Value("${part4.upload.path}")
    private String uploadPath;

    private final HomeworkFileHandler homeworkFileHandler;
    private final FileService fileService;
    private final JpaHomeworkRepository jpaHomeworkRepository;
    private final JpaHomeworkFileRepository jpaHomeworkFileRepository;
    private final JpaHomeworkLikeRepository jpaHomeworkLikeRepository;

    public Long saveHomework(Member member, HomeworkReqDto homeworkReqDto) {
        return jpaHomeworkRepository.save(homeworkReqDto.toEntity(member)).getId();
    }

    public Long fileId(List<HomeworkFile> homeworkFileList, Homework homework) {
        if (!homeworkFileList.isEmpty()) {
            for (HomeworkFile homeworkFile : homeworkFileList) {
                Long fileId = jpaHomeworkFileRepository.save(homeworkFile).getId();
                jpaHomeworkFileRepository.updateHomework(homework, fileId);
            }
        }
        return null;
    }

    @Transactional
    public void create(Member member, HomeworkReqDto homeworkReqDto, List<MultipartFile> files) throws Exception {
        List<HomeworkFile> homeworkFileList = homeworkFileHandler.parseFile(files, member.getStudentId());
        Long id = saveHomework(member, homeworkReqDto);
        Homework homework = jpaHomeworkRepository.getReferenceById(id);
        fileId(homeworkFileList, homework);
    }

    public List<Homework> viewHomework(boolean temp) {
        List<Homework> homework = jpaHomeworkRepository.findByTempFalse(Sort.by(Sort.Direction.ASC, "date"));
        if (temp) {
            homework = jpaHomeworkRepository.findByTempTrue(Sort.by(Sort.Direction.ASC, "date"));
        }
        return homework;
    }


    public List<HomeworkResDto> view() {
        List<Homework> homeworks = jpaHomeworkRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
        List<HomeworkResDto> homeworkResDto = new ArrayList<>();
        for (Homework homework : homeworks) {
            homeworkResDto.add(new HomeworkResDto(homework));
        }
        return homeworkResDto;
    }

    @Transactional
    public HomeworkResDto viewOne(Long id) {
        Homework homework = jpaHomeworkRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        int visit = homework.getVisit();
        jpaHomeworkRepository.updateVisit(visit + 1, id);
        Homework newHomework = jpaHomeworkRepository.getReferenceById(id);
        return new HomeworkResDto(newHomework);
    }



}
