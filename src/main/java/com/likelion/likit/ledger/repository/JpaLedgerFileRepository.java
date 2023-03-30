package com.likelion.likit.ledger.repository;

import com.likelion.likit.ledger.entity.Ledger;
import com.likelion.likit.ledger.entity.LedgerFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaLedgerFileRepository extends JpaRepository<LedgerFile, Long> {
    List<LedgerFile> findAllByLedgerId(Long ledgerId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE LedgerFile c SET c.ledger = :ledger WHERE c.id = :id ")
    void updateLedger(Ledger ledger, Long id);

}
