package com.likelion.likit.notice.dto;

import com.likelion.likit.notice.entity.Category;
import com.likelion.likit.notice.entity.Notice;
import com.likelion.likit.notice.entity.NoticeFile;
import com.likelion.likit.notice.entity.NoticeLikeMembers;
import com.likelion.likit.member.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class NoticeResDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private WriterDto member;
    private List<FileDto> file;
    private Long thumbnailId;
    private Category category;
    private List<Long> likeMemeber = new ArrayList<>();
    private Integer likes;
    private int visit;
    private String date;
    private boolean temp;
    private String creationDate;
    private String updateDate;

    public NoticeResDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.description = notice.getDescription();
        this.location = notice.getLocation();
        this.member = new WriterDto(notice.getMember());
        this.file = makeFileList(notice.getNoticeFiles());
        this.thumbnailId = new FileDto(notice.getThumbnail()).getId();
        this.category = notice.getCategory();
        List<NoticeLikeMembers> noticeLikeMembers = notice.getNoticeLikeMembers();
        for (NoticeLikeMembers likeMember : noticeLikeMembers) {
            this.likeMemeber.add(likeMember.getMember().getId());
        }
        this.likes = notice.getLikes();
        this.visit = notice.getVisit();
        this.date = notice.getDate();
        this.temp = notice.isTemp();
        this.creationDate = notice.getCreationDate();
        this.updateDate = notice.getUpdateDate();
    }

    public List<FileDto> makeFileList(List<NoticeFile> noticeFileList) {
        List<FileDto> fileList = new ArrayList<>();
        for (NoticeFile noticeFile : noticeFileList) {
            FileDto newFile = new FileDto(noticeFile);
            if (!newFile.isThumbnail) {
                fileList.add(newFile);
            }
        }
        return fileList;
    }

    @Getter
    private class FileDto {
        private Long id;
        private String fileName;
        private String filePath;
        private boolean isThumbnail;

        public FileDto(NoticeFile noticeFile) {
            this.id = noticeFile.getId();
            this.fileName = noticeFile.getFileName();
            this.filePath = noticeFile.getFilePath();
            this.isThumbnail = noticeFile.isThumbnail();
        }
    }

    @Getter
    private class WriterDto {
        private Long id;
        private String studentId;
        private String studentStudent;

        public WriterDto(Member member) {
            this.id = member.getId();
            this.studentId = member.getStudentId();
            this.studentStudent = member.getMemberDetails().getStudentName();
        }
    }
}
