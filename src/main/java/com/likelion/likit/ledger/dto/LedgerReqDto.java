package com.likelion.likit.ledger.dto;

import com.likelion.likit.ledger.entity.Ledger;
import com.likelion.likit.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LedgerReqDto {
    private String month;
    private String title;
    private String description;
    private String expenditure;
    private String revenue;
    private String carryoverAmount;
    private boolean temp;

    @Builder
    public LedgerReqDto(Ledger ledger) {
        this.month = ledger.getMonth();
        this.title = ledger.getTitle();
        this.description = ledger.getDescription();
        this.expenditure = ledger.getExpenditure();
        this.revenue = ledger.getRevenue();
        this.carryoverAmount = ledger.getCarryoverAmount();
        this.temp = ledger.isTemp();
    }

    public Ledger toEntity(Member member) {
        return Ledger.builder()
                .member(member)
                .month(month)
                .title(title)
                .description(description)
                .expenditure(expenditure)
                .revenue(revenue)
                .carryoverAmount(carryoverAmount)
                .temp(temp)
                .build();
    }
}
