package com.likelion.likit.diary.dto;

import com.likelion.likit.diary.entity.Category;
import com.likelion.likit.diary.entity.Diary;
import com.likelion.likit.member.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class DiaryResDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private WriterDto member;
    private List<Long> fileId;
    private Category category;
    private Integer likes;
    private int visit;
    private String creationDate;
    private String updateDate;

    public DiaryResDto(Diary diary, List<Long> fileId) {
        this.id = diary.getId();
        this.title = diary.getTitle();
        this.description = diary.getDescription();
        this.location = diary.getLocation();
        this.member = new WriterDto(diary.getMember());
        this.fileId = fileId;
        this.category = diary.getCategory();
        this.likes = diary.getLikes();
        this.visit = diary.getVisit();
        this.creationDate = diary.getCreationDate();
        this.updateDate = diary.getUpdateDate();
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
