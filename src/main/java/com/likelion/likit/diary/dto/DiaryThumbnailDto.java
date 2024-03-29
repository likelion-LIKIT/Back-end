package com.likelion.likit.diary.dto;

import com.likelion.likit.diary.entity.Category;
import com.likelion.likit.diary.entity.Diary;
import com.likelion.likit.diary.entity.DiaryFile;
import com.likelion.likit.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryThumbnailDto {
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

    public DiaryThumbnailDto(Diary diary) {
        this.id = diary.getId();
        this.title = diary.getTitle();
        this.description = diary.getDescription();
        this.location = diary.getLocation();
        this.member = new WriterDto(diary.getMember());
        this.thumbnailId = new ThumbnailDto(diary.getThumbnail()).getId();
        this.category = diary.getCategory();
        this.likes = diary.getLikes();
        this.visit = diary.getVisit();
        this.date = diary.getDate();
        this.creationDate = diary.getCreationDate();
        this.updateDate = diary.getUpdateDate();
    }

    @Getter
    private class ThumbnailDto{
        private Long id;

        public ThumbnailDto(DiaryFile diaryFile) {
            this.id = diaryFile.getId();
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

