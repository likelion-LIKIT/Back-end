package com.likelion.likit.ledger;

import com.likelion.likit.ledger.dto.LedgerReqDto;
import com.likelion.likit.ledger.dto.LedgerResDto;
import com.likelion.likit.ledger.entity.Ledger;
import com.likelion.likit.ledger.entity.LedgerFile;
import com.likelion.likit.ledger.repository.JpaLedgerRepository;
import com.likelion.likit.exception.CustomException;
import com.likelion.likit.exception.ExceptionEnum;
import com.likelion.likit.ledger.repository.JpaLedgerFileRepository;
import com.likelion.likit.file.FileService;
import com.likelion.likit.file.entity.ImageFile;
import com.likelion.likit.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LedgerService {

    @Value("${part4.upload.path}")
    private String uploadPath;

    private final LedgerFileHandler ledgerFileHandler;
    private final FileService fileService;
    private final JpaLedgerRepository jpaLedgerRepository;
    private final JpaLedgerFileRepository jpaLedgerFileRepository;

    public Long saveLedger(Member member, LedgerReqDto ledgerReqDto) {
        return jpaLedgerRepository.save(ledgerReqDto.toEntity(member)).getId();
    }

    public Long fileId(List<LedgerFile> ledgerFileList, Ledger ledger) {
        if (!ledgerFileList.isEmpty()) {
            for (LedgerFile ledgerFile : ledgerFileList) {
                Long fileId = jpaLedgerFileRepository.save(ledgerFile).getId();
                jpaLedgerFileRepository.updateLedger(ledger, fileId);
            }
        }
        return null;
    }

    @Transactional
    public void create(Member member, LedgerReqDto ledgerReqDto, List<MultipartFile> files) throws Exception {
        List<LedgerFile> ledgerFileList = ledgerFileHandler.parseFile(files, member.getStudentId());
        Long id = saveLedger(member, ledgerReqDto);
        Ledger ledger = jpaLedgerRepository.getReferenceById(id);
        fileId(ledgerFileList, ledger);
    }

    public List<Ledger> viewLedger(boolean temp) {
        List<Ledger> ledgers = jpaLedgerRepository.findByTempFalse(Sort.by(Sort.Direction.ASC, "creationDate"));
        if (temp) {
            ledgers = jpaLedgerRepository.findByTempTrue(Sort.by(Sort.Direction.ASC, "creationDate"));
        }
        return ledgers;
    }


    public List<LedgerResDto> view() {
        List<Ledger> ledgers = jpaLedgerRepository.findAll(Sort.by(Sort.Direction.ASC, "creationDate"));
        List<LedgerResDto> ledgerResDto = new ArrayList<>();
        for (Ledger ledger : ledgers) {
            ledgerResDto.add(new LedgerResDto(ledger));
        }
        return ledgerResDto;
    }


    public LedgerResDto viewOne(Long id) {
        Ledger ledger = jpaLedgerRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionEnum.NOTEXIST));
        int visit = ledger.getVisit();
        jpaLedgerRepository.updateVisit(visit + 1, id);
        Ledger newLedger = jpaLedgerRepository.getReferenceById(id);
        return new LedgerResDto(newLedger);
    }




}
