package com.likelion.likit.diary;

import com.likelion.likit.diary.dto.DiaryReqDto;
import com.likelion.likit.diary.dto.DiaryThumbnailDto;
import com.likelion.likit.diary.entity.Diary;
import com.likelion.likit.diary.repository.JpaDiaryRepository;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {

    private final FileHandler fileHandler;
    private final FileService fileService;
    private final JpaDiaryRepository jpaDiaryRepository;
    private final JpaFileRepository jpaFileRepository;

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
//        DiaryResDto diaryResDto = new DiaryResDto(diary);
//        return diaryResDto;
    }


    public List<Diary> viewDiary() {
        return jpaDiaryRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
    }

    public List<DiaryThumbnailDto> viewDiaryWithThumbnail() {
        List<Diary> diaries = jpaDiaryRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
        List<DiaryThumbnailDto> diaryThumbnailDtos = new ArrayList<>();
        for (Diary diary : diaries){
            System.out.println(diary.getThumbnail().getId());
            diaryThumbnailDtos.add(new DiaryThumbnailDto(diary));
        }
        return diaryThumbnailDtos;
    }
}
