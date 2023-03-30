package com.likelion.likit.homework.dto;

import com.likelion.likit.homework.entity.Category;
import com.likelion.likit.homework.entity.Homework;
import com.likelion.likit.homework.entity.HomeworkFile;
import com.likelion.likit.homework.entity.HomeworkLikeMembers;
import com.likelion.likit.member.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class HomeworkResDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private WriterDto member;
    private List<FileDto> file;
    private Category category;
    private List<Long> likeMemeber = new ArrayList<>();
    private Integer likes;
    private int visit;
    private String date;
    private boolean temp;
    private String creationDate;
    private String updateDate;

    public HomeworkResDto(Homework homework) {
        this.id = homework.getId();
        this.title = homework.getTitle();
        this.description = homework.getDescription();
        this.location = homework.getLocation();
        this.member = new WriterDto(homework.getMember());
        this.file = makeFileList(homework.getHomeworkFiles());
        this.category = homework.getCategory();
        List<HomeworkLikeMembers> homeworkLikeMembers = homework.getHomeworkLikeMembers();
        for (HomeworkLikeMembers likeMember : homeworkLikeMembers) {
            this.likeMemeber.add(likeMember.getMember().getId());
        }
        this.likes = homework.getLikes();
        this.visit = homework.getVisit();
        this.date = homework.getDate();
        this.temp = homework.isTemp();
        this.creationDate = homework.getCreationDate();
        this.updateDate = homework.getUpdateDate();
    }

    public List<FileDto> makeFileList(List<HomeworkFile> homeworkFileList) {
        List<FileDto> fileList = new ArrayList<>();
        for (HomeworkFile homeworkFile : homeworkFileList) {
            FileDto newFile = new FileDto(homeworkFile);
            fileList.add(newFile);
        }
        return fileList;
    }

    @Getter
    private class FileDto {
        private Long id;
        private String fileName;
        private String filePath;

        public FileDto(HomeworkFile homeworkFile) {
            this.id = homeworkFile.getId();
            this.fileName = homeworkFile.getFileName();
            this.filePath = homeworkFile.getFilePath();
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
