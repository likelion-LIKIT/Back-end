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
import com.likelion.likit.diary.entity.DiaryFile;
import com.likelion.likit.diary.repository.JpaDiaryFileRepository;
import com.likelion.likit.file.FileDto;
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
public class DiaryService {

    @Value("${part4.upload.path}")
    private String uploadPath;

    private final DiaryFileHandler diaryFileHandler;
    private final JpaDiaryRepository jpaDiaryRepository;
    private final JpaDiaryFileRepository jpaDiaryFileRepository;
    private final JpaDiaryLikeRepository jpaDiaryLikeRepository;

    public Long saveDiary(Member member, DiaryFile thumbnail, DiaryReqDto diaryReqDto) {
        return jpaDiaryRepository.save(diaryReqDto.toEntity(member, thumbnail)).getId();
    }

    public Long fileId(List<DiaryFile> diaryFileList, Diary diary) {
        if (!diaryFileList.isEmpty()) {
            for (DiaryFile diaryFile : diaryFileList) {
                Long fileId = jpaDiaryFileRepository.save(diaryFile).getId();
                jpaDiaryFileRepository.updatediary(diary, fileId);
            }
        }
        return null;
    }

    @Transactional
    public void create(Member member, DiaryReqDto diaryReqDto, List<MultipartFile> thumbnail, List<MultipartFile> files) throws Exception {
        List<DiaryFile> diaryFileList = diaryFileHandler.parseFile(files, false);
        List<DiaryFile> thumbnailDiaryFile = diaryFileHandler.parseFile(thumbnail, true);
        Long id = saveDiary(member, thumbnailDiaryFile.get(0), diaryReqDto);
        Diary diary = jpaDiaryRepository.getReferenceById(id);
        fileId(diaryFileList, diary);
        fileId(thumbnailDiaryFile, diary);
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
        jpaDiaryRepository.updateVisit(visit + 1, id);
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
                DiaryFile baseThumbnail = jpaDiaryFileRepository.findByDiaryIdAndIsThumbnail(id, true);
                jpaDiaryFileRepository.delete(baseThumbnail);
                List<DiaryFile> thumbnailDiaryFile = diaryFileHandler.parseFile(thumbnail, true);
                fileId(thumbnailDiaryFile, diary);
            }
            if (files != null) {
                List<DiaryFile> baseDiaryFileList = jpaDiaryFileRepository.findAllByDiaryIdAndIsThumbnail(id, false);
                List<DiaryFile> diaryFileList = diaryFileHandler.parseFile(files, false);
                for (DiaryFile diaryFile : baseDiaryFileList) {
                    jpaDiaryFileRepository.delete(diaryFile);
                }
                fileId(diaryFileList, diary);
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
        if (!liked.isPresent()) {
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
        if (liked.isPresent()) {
            return "LIKED";
        } else {
            return "UNLIKED";
        }
    }

    @Transactional(readOnly = true)
    public FileDto findDiaryByFileId(Long id) {
        DiaryFile diaryFile = jpaDiaryFileRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.FILENOTEXIST));

        FileDto fileDto = FileDto.builder()
                .fileName(diaryFile.getFileName())
                .filePath(diaryFile.getFilePath())
                .fileSize(diaryFile.getFileSize())
                .isThumbnail(diaryFile.isThumbnail())
                .build();

        return fileDto;
    }

    public ResponseEntity<Object> download(Long fileID) throws IOException {
        DiaryFile diaryFile = jpaDiaryFileRepository.findById(fileID).orElseThrow(() -> new CustomException(ExceptionEnum.FILENOTEXIST));
        String filePath = diaryFile.getFilePath();
        try {
            Resource resource = diaryFileHandler.download(filePath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(diaryFile.getFileName(), StandardCharsets.UTF_8).build());
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    public String findDiaryByDiaryId(Long id, String fileName) throws IOException {
        DiaryFile diaryInfo = null;
        List<DiaryFile> diaryFile = jpaDiaryFileRepository.findAllByDiaryId(id);
        System.out.println(diaryFile);
        String enFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        for (DiaryFile diaryFile1 : diaryFile) {
            String a = diaryFile1.getFileName();
            String enCompare = URLEncoder.encode(a, StandardCharsets.UTF_8);
            System.out.println(enCompare + " " + enFileName);
            if (enCompare.equals(enFileName)) {
                diaryInfo = diaryFile1;
            }
        }
        FileDto fileDto = FileDto.builder()
                .fileName(diaryInfo.getFileName())
                .filePath(diaryInfo.getFilePath())
                .fileSize(diaryInfo.getFileSize())
                .isThumbnail(diaryInfo.isThumbnail())
                .build();

        return uploadPath + fileDto.getFilePath();
    }
}
