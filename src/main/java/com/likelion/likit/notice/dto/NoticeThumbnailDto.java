package com.likelion.likit.notice.dto;

import com.likelion.likit.notice.entity.Category;
import com.likelion.likit.notice.entity.Notice;
import com.likelion.likit.notice.entity.NoticeFile;
import com.likelion.likit.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeThumbnailDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private WriterDto member;
    private Long thumbnailId;
    private Category category;
    private Integer likes;
    private int visit;
    private String date;
    private String creationDate;
    private String updateDate;

    public NoticeThumbnailDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.description = notice.getDescription();
        this.location = notice.getLocation();
        this.member = new WriterDto(notice.getMember());
        this.thumbnailId = new ThumbnailDto(notice.getThumbnail()).getId();
        this.category = notice.getCategory();
        this.likes = notice.getLikes();
        this.visit = notice.getVisit();
        this.date = notice.getDate();
        this.creationDate = notice.getCreationDate();
        this.updateDate = notice.getUpdateDate();
    }

    @Getter
    private class ThumbnailDto{
        private Long id;

        public ThumbnailDto(NoticeFile noticeFile) {
            this.id = noticeFile.getId();
        }
    }

    @Getter
    private class WriterDto{
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

