package com.likelion.likit.diary;

import com.likelion.likit.diary.dto.DiaryReqDto;
import com.likelion.likit.diary.dto.DiaryResDto;
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

    public Long saveDiary(Member member, DiaryReqDto diaryReqDto) {
        return jpaDiaryRepository.save(diaryReqDto.toEntity(member)).getId();
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
    public DiaryResDto create(Member member, DiaryReqDto diaryReqDto, List<MultipartFile> files) throws Exception {
        Long id = saveDiary(member, diaryReqDto);
        List<File> fileList = fileHandler.parseFile(files);
        Diary diary = jpaDiaryRepository.getReferenceById(id);
        fileId(fileList, diary);
        fileService.findAllByDiary(id);
        List<Long> fileId = new ArrayList<>();
        DiaryResDto diaryResDto = new DiaryResDto(diary, fileId);
        return diaryResDto;
    }


    public List<Diary> viewDiary() {
        return jpaDiaryRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
    }
}
