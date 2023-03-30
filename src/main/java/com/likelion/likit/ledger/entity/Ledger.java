package com.likelion.likit.ledger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.likit.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Ledger {


    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "month")
    private String month;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String expenditure;
    private String revenue;
    private String carryoverAmount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "ledger", cascade = {CascadeType.PERSIST, CascadeType.REMOVE} , orphanRemoval = true)
    private List<LedgerFile> ledgerFiles = new ArrayList<>();

    @ColumnDefault("0")
    private int visit;

    private boolean temp;

    @Column(name = "creation_date") @CreatedDate
    private String creationDate;

    @Column(name = "update_date") @CreatedDate
    private String updateDate;

    @Builder
    public Ledger(String title, String description, String expenditure, String revenue, String carryoverAmount,
                  Member member, List<LedgerFile> ledgerFiles, int visit, String month, boolean temp) {
        this.title = title;
        this.description = description;
        this.expenditure = expenditure;
        this.revenue = revenue;
        this.carryoverAmount = carryoverAmount;
        this.member = member;
        this.ledgerFiles = ledgerFiles;
        this.visit = visit;
        this.month = String.format(month, DateTimeFormatter.ofPattern("MM"));
        this.temp = temp;
        this.creationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
    }
}
