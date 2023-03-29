package com.likelion.likit.diary.dto;

import com.likelion.likit.diary.entity.Category;
import com.likelion.likit.diary.entity.Diary;
import com.likelion.likit.diary.entity.LikeMembers;
import com.likelion.likit.diary.entity.DiaryFile;
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

    public DiaryResDto(Diary diary) {
        this.id = diary.getId();
        this.title = diary.getTitle();
        this.description = diary.getDescription();
        this.location = diary.getLocation();
        this.member = new WriterDto(diary.getMember());
        this.file = makeFileList(diary.getDiaryFiles());
        this.thumbnailId = new FileDto(diary.getThumbnail()).getId();
        this.category = diary.getCategory();
        List<LikeMembers> likeMembers = diary.getLikeMembers();
        for (LikeMembers likeMember : likeMembers) {
            this.likeMemeber.add(likeMember.getMember().getId());
        }
        this.likes = diary.getLikes();
        this.visit = diary.getVisit();
        this.date = diary.getDate();
        this.temp = diary.isTemp();
        this.creationDate = diary.getCreationDate();
        this.updateDate = diary.getUpdateDate();
    }

    public List<FileDto> makeFileList(List<DiaryFile> diaryFileList) {
        List<FileDto> fileList = new ArrayList<>();
        for (DiaryFile diaryFile : diaryFileList) {
            FileDto newFile = new FileDto(diaryFile);
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

        public FileDto(DiaryFile diaryFile) {
            this.id = diaryFile.getId();
            this.fileName = diaryFile.getFileName();
            this.filePath = diaryFile.getFilePath();
            this.isThumbnail = diaryFile.isThumbnail();
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
