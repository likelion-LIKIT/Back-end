package com.likelion.likit.ledger.dto;

import com.likelion.likit.ledger.entity.Ledger;
import com.likelion.likit.ledger.entity.LedgerFile;
import com.likelion.likit.member.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class LedgerResDto {
    private Long id;
    private String month;
    private String title;
    private String description;
    private String expenditure;
    private String revenue;
    private String carryoverAmount;
    private WriterDto member;
    private List<FileDto> file;
    private int visit;
    private boolean temp;
    private String creationDate;
    private String updateDate;

    public LedgerResDto(Ledger ledger) {
        this.id = ledger.getId();
        this.month = ledger.getMonth();
        this.title = ledger.getTitle();
        this.description = ledger.getDescription();
        this.expenditure = ledger.getExpenditure();
        this.revenue = ledger.getRevenue();
        this.carryoverAmount = ledger.getCarryoverAmount();
        this.member = new WriterDto(ledger.getMember());
        this.file = makeFileList(ledger.getLedgerFiles());
        this.visit = ledger.getVisit();
        this.temp = ledger.isTemp();
        this.creationDate = ledger.getCreationDate();
        this.updateDate = ledger.getUpdateDate();
    }

    public List<FileDto> makeFileList(List<LedgerFile> ledgerFileList) {
        List<FileDto> fileList = new ArrayList<>();
        for (LedgerFile ledgerFile : ledgerFileList) {
            FileDto newFile = new FileDto(ledgerFile);
            fileList.add(newFile);
        }
        return fileList;
    }

    @Getter
    private class FileDto {
        private Long id;
        private String fileName;
        private String filePath;

        public FileDto(LedgerFile ledgerFile) {
            this.id = ledgerFile.getId();
            this.fileName = ledgerFile.getFileName();
            this.filePath = ledgerFile.getFilePath();
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
