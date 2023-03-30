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

    @Transactional
    public void update(Long id, Member member, HomeworkReqDto homeworkReqDto, List<MultipartFile> files) throws Exception {
        Homework homework = jpaHomeworkRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        if (homework.getMember() == member) {
            if (homeworkReqDto != null) {
                String title = homeworkReqDto.getTitle();
                String description = homeworkReqDto.getDescription();
                String location = homeworkReqDto.getLocation();
                Category category = homeworkReqDto.getCategory();
                String date = homeworkReqDto.getDate();
                boolean temp = homeworkReqDto.isTemp();
                String updateDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
                if (title != null) {
                    jpaHomeworkRepository.updateTitle(title, id);
                }
                if (description != null) {
                    jpaHomeworkRepository.updateDescription(description, id);
                }
                if (location != null) {
                    jpaHomeworkRepository.updateLocation(location, id);
                }
                if (category != null) {
                    jpaHomeworkRepository.updateCategory(category, id);
                }
                if (date != null) {
                    jpaHomeworkRepository.updateDate(date, id);
                }
                if (!temp) {
                    jpaHomeworkRepository.updateTemp(false, id);
                }
                jpaHomeworkRepository.updateUpdateDate(updateDateTime, id);
            }
            if (files != null) {
                List<HomeworkFile> baseHomeworkFileList = jpaHomeworkFileRepository.findAllByHomeworkId(id);
                List<HomeworkFile> homeworkFileList = homeworkFileHandler.parseFile(files, member.getStudentId());
                for (HomeworkFile homeworkFile : baseHomeworkFileList) {
                    jpaHomeworkFileRepository.delete(homeworkFile);
                }
                fileId(homeworkFileList, homework);
            }
        } else {
            throw new CustomException(ExceptionEnum.StudentIdNotMatched);
        }
    }

    @Transactional
    public void delete(Long id, Member member) {
        Homework homework = jpaHomeworkRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        if (member == homework.getMember()) {
            jpaHomeworkRepository.delete(homework);
        } else {
            throw new CustomException(ExceptionEnum.StudentIdNotMatched);
        }
    }

    @Transactional
    public String like(Long id, Member member) {
        String result = "LIKE";
        Homework homework = jpaHomeworkRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        Optional<HomeworkLikeMembers> liked = jpaHomeworkLikeRepository.findByHomeworkAndMember(homework, member);
        if (!liked.isPresent()) {
            HomeworkLikeMembers likeMember = HomeworkLikeMembers.builder()
                    .homework(homework)
                    .member(member)
                    .build();
            jpaHomeworkLikeRepository.save(likeMember);
        } else {
            jpaHomeworkLikeRepository.delete(liked.get());
            result = "UNLIKE";
        }
        Integer count = jpaHomeworkLikeRepository.findByHomeworkId(id).size();
        jpaHomeworkRepository.updateLikes(count, id);
        return result;
    }

    public String checkLike(Long id, Member member) {
        Homework homework = jpaHomeworkRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        Optional<HomeworkLikeMembers> liked = jpaHomeworkLikeRepository.findByHomeworkAndMember(homework, member);
        if (liked.isPresent()) {
            return "LIKED";
        } else {
            return "UNLIKED";
        }
    }

    public List<String> likeList(Long id) {
        List<String> likeList = new ArrayList<>();
        Homework homework = jpaHomeworkRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        List<HomeworkLikeMembers> likeMembers = homework.getHomeworkLikeMembers();
        for (HomeworkLikeMembers member : likeMembers) {
            likeList.add(member.getMember().getUsername());
        }
        return likeList;
    }






}
