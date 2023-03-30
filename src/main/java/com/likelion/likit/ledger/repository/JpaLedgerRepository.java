package com.likelion.likit.ledger.repository;

import com.likelion.likit.ledger.entity.Ledger;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaLedgerRepository extends JpaRepository<Ledger, Long> {
    List<Ledger> findByTempTrue(Sort sort);
    List<Ledger> findByTempFalse(Sort sort);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Ledger c SET c.month = :month WHERE c.id = :id ")
    void updateMonth(String month, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Ledger c SET c.title = :title WHERE c.id = :id ")
    void updateTitle(String title, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Ledger c SET c.description = :description WHERE c.id = :id ")
    void updateDescription(String description, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Ledger c SET c.expenditure = :expenditure WHERE c.id = :id ")
    void updateExpenditure(String expenditure, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Ledger c SET c.revenue = :revenue WHERE c.id = :id ")
    void updateRevenue(String revenue, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Ledger c SET c.carryoverAmount = :carryoverAmount WHERE c.id = :id ")
    void updateCarryoverAmount(String carryoverAmount, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Ledger c SET c.visit = :visit WHERE c.id = :id ")
    void updateVisit(int visit, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Ledger c SET c.updateDate = :updateDate WHERE c.id = :id ")
    void updateUpdateDate(String updateDate, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Ledger c SET c.temp = :temp WHERE c.id = :id ")
    void updateTemp(boolean temp, Long id);
}
