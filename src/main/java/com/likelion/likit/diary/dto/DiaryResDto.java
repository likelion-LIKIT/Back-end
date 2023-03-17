package com.likelion.likit.diary.dto;

import com.likelion.likit.diary.entity.Category;
import com.likelion.likit.diary.entity.Diary;
import com.likelion.likit.file.entity.File;
import com.likelion.likit.member.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private Long thumbnailId;
    private Category category;
    private Integer likes;
    private int visit;
    private String date;
    private String creationDate;
    private String updateDate;

    public DiaryResDto(Diary diary) {
        this.id = diary.getId();
        this.title = diary.getTitle();
        this.description = diary.getDescription();
        this.location = diary.getLocation();
        this.member = new WriterDto(diary.getMember());
        this.fileId = makeFileList(diary.getFiles());
        this.thumbnailId = new FileDto(diary.getThumbnail()).getId();
        this.category = diary.getCategory();
        this.likes = diary.getLikes();
        this.visit = diary.getVisit();
        this.date = diary.getDate();
        this.creationDate = diary.getCreationDate();
        this.updateDate = diary.getUpdateDate();
    }

    public List<Long> makeFileList(List<File> fileList) {
        List<Long> fileIdList = new ArrayList<>();
        for (File file : fileList) {
            FileDto newFile = new FileDto(file);
            if (!newFile.isThumbnail) {
                fileIdList.add(newFile.getId());
            }
        }
        return fileIdList;
    }

    @Getter
    private class FileDto {
        private Long id;
        private boolean isThumbnail;

        public FileDto(File file) {
            this.id = file.getId();
            this.isThumbnail = file.isThumbnail();
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
