package com.likelion.likit.notice;

import com.likelion.likit.notice.dto.NoticeReqDto;
import com.likelion.likit.notice.dto.NoticeResDto;
import com.likelion.likit.notice.dto.NoticeThumbnailDto;
import com.likelion.likit.notice.entity.Category;
import com.likelion.likit.notice.entity.Notice;
import com.likelion.likit.notice.entity.NoticeLikeMembers;
import com.likelion.likit.notice.entity.NoticeFile;
import com.likelion.likit.notice.repository.JpaNoticeRepository;
import com.likelion.likit.notice.repository.JpaNoticeLikeRepository;
import com.likelion.likit.exception.CustomException;
import com.likelion.likit.exception.ExceptionEnum;
import com.likelion.likit.notice.repository.JpaNoticeFileRepository;
import com.likelion.likit.file.FileDto;
import com.likelion.likit.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class NoticeService {

    private final NoticeFileHandler noticeFileHandler;
    private final JpaNoticeRepository jpaNoticeRepository;
    private final JpaNoticeFileRepository jpaNoticeFileRepository;
    private final JpaNoticeLikeRepository jpaNoticeLikeRepository;

    public Long saveNotice(Member member, NoticeFile thumbnail, NoticeReqDto noticeReqDto) {
        return jpaNoticeRepository.save(noticeReqDto.toEntity(member, thumbnail)).getId();
    }

    public Long fileId(List<NoticeFile> noticeFileList, Notice notice) {
        if (!noticeFileList.isEmpty()) {
            for (NoticeFile noticeFile : noticeFileList) {
                Long fileId = jpaNoticeFileRepository.save(noticeFile).getId();
                jpaNoticeFileRepository.updateNotice(notice, fileId);
            }
        }
        return null;
    }

    @Transactional
    public void create(Member member, NoticeReqDto noticeReqDto, List<MultipartFile> thumbnail, List<MultipartFile> files) throws Exception {
        List<NoticeFile> noticeFileList = noticeFileHandler.parseFile(files, false);
        List<NoticeFile> thumbnailNoticeFile = noticeFileHandler.parseFile(thumbnail, true);
        Long id = saveNotice(member, thumbnailNoticeFile.get(0), noticeReqDto);
        Notice notice = jpaNoticeRepository.getReferenceById(id);
        fileId(noticeFileList, notice);
        fileId(thumbnailNoticeFile, notice);
    }


    public List<Notice> viewNotice() {
        return jpaNoticeRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
    }

    public List<NoticeThumbnailDto> viewNoticeWithThumbnail() {
        List<Notice> diaries = jpaNoticeRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
        List<NoticeThumbnailDto> noticeThumbnailDtos = new ArrayList<>();
        for (Notice notice : diaries) {
            System.out.println(notice.getThumbnail().getId());
            noticeThumbnailDtos.add(new NoticeThumbnailDto(notice));
        }
        return noticeThumbnailDtos;
    }

    public List<NoticeResDto> view() {
        List<Notice> diaries = jpaNoticeRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
        List<NoticeResDto> noticeResDto = new ArrayList<>();
        for (Notice notice : diaries) {
            System.out.println(notice.getThumbnail().getId());
            noticeResDto.add(new NoticeResDto(notice));
        }
        return noticeResDto;
    }

    @Transactional
    public NoticeResDto viewOne(Long id) {
        Notice notice = jpaNoticeRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        int visit = notice.getVisit();
        jpaNoticeRepository.updateVisit(visit + 1, id);
        Notice newNotice = jpaNoticeRepository.getReferenceById(id);
        return new NoticeResDto(newNotice);
    }

    @Transactional
    public void update(Long id, Member member, NoticeReqDto noticeReqDto, List<MultipartFile> thumbnail, List<MultipartFile> files) throws Exception {
        Notice notice = jpaNoticeRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        if (notice.getMember() == member) {
            if (noticeReqDto != null) {
                String title = noticeReqDto.getTitle();
                String description = noticeReqDto.getDescription();
                String location = noticeReqDto.getLocation();
                Category category = noticeReqDto.getCategory();
                String date = noticeReqDto.getDate();
                String updateDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
                if (title != null) {
                    jpaNoticeRepository.updateTitle(title, id);
                }
                if (description != null) {
                    jpaNoticeRepository.updateDescription(description, id);
                }
                if (location != null) {
                    jpaNoticeRepository.updateLocation(location, id);
                }
                if (category != null) {
                    jpaNoticeRepository.updateCategory(category, id);
                }
                if (date != null) {
                    jpaNoticeRepository.updateDate(date, id);
                }
                jpaNoticeRepository.updateUpdateDate(updateDateTime, id);
            }
            if (thumbnail != null) {
                NoticeFile baseThumbnail = jpaNoticeFileRepository.findByNoticeIdAndIsThumbnail(id, true);
                jpaNoticeFileRepository.delete(baseThumbnail);
                List<NoticeFile> thumbnailNoticeFile = noticeFileHandler.parseFile(thumbnail, true);
                fileId(thumbnailNoticeFile, notice);
            }
            if (files != null) {
                List<NoticeFile> baseNoticeFileList = jpaNoticeFileRepository.findAllByNoticeIdAndIsThumbnail(id, false);
                List<NoticeFile> noticeFileList = noticeFileHandler.parseFile(files, false);
                for (NoticeFile noticeFile : baseNoticeFileList) {
                    jpaNoticeFileRepository.delete(noticeFile);
                }
                fileId(noticeFileList, notice);
            }
        } else {
            throw new CustomException(ExceptionEnum.StudentIdNotMatched);
        }
    }

    @Transactional
    public void delete(Long id, Member member) {
        Notice notice = jpaNoticeRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        if (member == notice.getMember()) {
            jpaNoticeRepository.delete(notice);
        } else {
            throw new CustomException(ExceptionEnum.StudentIdNotMatched);
        }
    }


}
