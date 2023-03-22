package com.likelion.likit.diary;

import com.likelion.likit.diary.dto.DiaryReqDto;
import com.likelion.likit.diary.dto.DiaryResDto;
import com.likelion.likit.diary.dto.DiaryThumbnailDto;
import com.likelion.likit.diary.entity.Category;
import com.likelion.likit.diary.entity.Diary;
import com.likelion.likit.diary.entity.LikeMembers;
import com.likelion.likit.diary.repository.JpaDiaryRepository;
import com.likelion.likit.diary.repository.JpaDiaryLikeRepository;
import com.likelion.likit.exception.CustomException;
import com.likelion.likit.exception.ExceptionEnum;
import com.likelion.likit.file.FileHandler;
import com.likelion.likit.file.FileService;
import com.likelion.likit.file.entity.File;
import com.likelion.likit.file.repository.JpaFileRepository;
import com.likelion.likit.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {

    private final FileHandler fileHandler;
    private final FileService fileService;
    private final JpaDiaryRepository jpaDiaryRepository;
    private final JpaFileRepository jpaFileRepository;
    private final JpaDiaryLikeRepository jpaDiaryLikeRepository;

    public Long saveDiary(Member member, File thumbnail, DiaryReqDto diaryReqDto) {
        return jpaDiaryRepository.save(diaryReqDto.toEntity(member, thumbnail)).getId();
    }

    public Long fileId(List<File> fileList, Diary diary) {
        if (!fileList.isEmpty()) {
            for (File file : fileList) {
                Long fileId = jpaFileRepository.save(file).getId();
                jpaFileRepository.updatediary(diary, fileId);
            }
        }
        return null;
    }

    @Transactional
    public void create(Member member, DiaryReqDto diaryReqDto, List<MultipartFile> thumbnail, List<MultipartFile> files) throws Exception {
        List<File> fileList = fileHandler.parseFile(files, false);
        List<File> thumbnailFile = fileHandler.parseFile(thumbnail, true);
        Long id = saveDiary(member, thumbnailFile.get(0), diaryReqDto);
        Diary diary = jpaDiaryRepository.getReferenceById(id);
        fileId(fileList, diary);
        fileId(thumbnailFile, diary);
    }


    public List<Diary> viewDiary() {
        return jpaDiaryRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
    }

    public List<DiaryThumbnailDto> viewDiaryWithThumbnail() {
        List<Diary> diaries = jpaDiaryRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
        List<DiaryThumbnailDto> diaryThumbnailDtos = new ArrayList<>();
        for (Diary diary : diaries) {
            System.out.println(diary.getThumbnail().getId());
            diaryThumbnailDtos.add(new DiaryThumbnailDto(diary));
        }
        return diaryThumbnailDtos;
    }

    public List<DiaryResDto> view() {
        List<Diary> diaries = jpaDiaryRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
        List<DiaryResDto> diaryResDto = new ArrayList<>();
        for (Diary diary : diaries) {
            System.out.println(diary.getThumbnail().getId());
            diaryResDto.add(new DiaryResDto(diary));
        }
        return diaryResDto;
    }

    @Transactional
    public DiaryResDto viewOne(Long id) {
        Diary diary = jpaDiaryRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        int visit = diary.getVisit();
        jpaDiaryRepository.updateVisit(visit+1, id);
        Diary newDiary = jpaDiaryRepository.getReferenceById(id);
        return new DiaryResDto(newDiary);
    }

    @Transactional
    public void update(Long id, Member member, DiaryReqDto diaryReqDto, List<MultipartFile> thumbnail, List<MultipartFile> files) throws Exception {
        Diary diary = jpaDiaryRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        if (diary.getMember() == member) {
            if (diaryReqDto != null) {
                String title = diaryReqDto.getTitle();
                String description = diaryReqDto.getDescription();
                String location = diaryReqDto.getLocation();
                Category category = diaryReqDto.getCategory();
                String date = diaryReqDto.getDate();
                String updateDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
                if (title != null) {
                    jpaDiaryRepository.updateTitle(title, id);
                }
                if (description != null) {
                    jpaDiaryRepository.updateDescription(description, id);
                }
                if (location != null) {
                    jpaDiaryRepository.updateLocation(location, id);
                }
                if (category != null) {
                    jpaDiaryRepository.updateCategory(category, id);
                }
                if (date != null) {
                    jpaDiaryRepository.updateDate(date, id);
                }
                jpaDiaryRepository.updateUpdateDate(updateDateTime, id);
            }
            if (thumbnail != null) {
                File baseThumbnail = jpaFileRepository.findByDiaryIdAndIsThumbnail(id, true);
                jpaFileRepository.delete(baseThumbnail);
                List<File> thumbnailFile = fileHandler.parseFile(thumbnail, true);
                fileId(thumbnailFile, diary);
            }
            if (files != null) {
                List<File> baseFileList = jpaFileRepository.findAllByDiaryIdAndIsThumbnail(id, false);
                List<File> fileList = fileHandler.parseFile(files, false);
                for (File file : baseFileList) {
                    jpaFileRepository.delete(file);
                }
                fileId(fileList, diary);
            }
        } else {
            throw new CustomException(ExceptionEnum.StudentIdNotMatched);
        }
    }

    @Transactional
    public void delete(Long id, Member member) {
        Diary diary = jpaDiaryRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        if (member == diary.getMember()) {
            jpaDiaryRepository.delete(diary);
        } else {
            throw new CustomException(ExceptionEnum.StudentIdNotMatched);
        }
    }

    @Transactional
    public String like(Long id, Member member) {
        String result = "LIKE";
        Diary diary = jpaDiaryRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        Optional<LikeMembers> liked = jpaDiaryLikeRepository.findByDiaryAndMember(diary, member);
        if (!liked.isPresent()){
            LikeMembers likeMember = LikeMembers.builder()
                    .diary(diary)
                    .member(member)
                    .build();
            jpaDiaryLikeRepository.save(likeMember);
        } else {
            jpaDiaryLikeRepository.delete(liked.get());
            result = "UNLIKE";
        }
        Integer count = jpaDiaryLikeRepository.findByDiaryId(id).size();
        jpaDiaryRepository.updateLikes(count, id);
        return result;
    }

    public String checkLike(Long id, Member member) {
        Diary diary = jpaDiaryRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        Optional<LikeMembers> liked = jpaDiaryLikeRepository.findByDiaryAndMember(diary, member);
        if (liked.isPresent()){
            return "LIKED";
        } else {
            return "UNLIKED";
        }
    }
}
