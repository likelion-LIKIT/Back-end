package com.likelion.likit.ledger;

import com.likelion.likit.ledger.dto.LedgerReqDto;
import com.likelion.likit.ledger.dto.LedgerResDto;
import com.likelion.likit.ledger.entity.Ledger;
import com.likelion.likit.member.MemberController;
import com.likelion.likit.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LedgerController {

    private final MemberController memberController;
    private final LedgerService ledgerService;

    @Operation(summary = "ledger 글 작성", description = "성공하면 게시글이 Ledger 데이터베이스에 저장")
    @PostMapping("/ledger")
    public ResponseEntity<String> create(@RequestHeader String accessToken,
                                         @RequestPart(value = "ledgerReqDto") LedgerReqDto ledgerReqDto,
                                         @RequestPart(value = "file", required = false)List<MultipartFile> files) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        ledgerService.create(member, ledgerReqDto, files);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Operation(summary = "ledger 글 조회", description = "Ledger 글 조희")
    @GetMapping("/ledger")
    public List<Ledger> viewLedger(@RequestParam(name = "temp", defaultValue = "false", required = false) boolean temp ) {
        return ledgerService.viewLedger(temp);
    }

    @Operation(summary = "ledger 글 정보 모두 조회", description = "Ledger 글 조희")
    @GetMapping("/ledger/all")
    public List<LedgerResDto> viewAll() {
        return ledgerService.view();
    }

    @Operation(summary = "ledger id 글 정보 모두 조회", description = "해당 Ledger 글 조희 + 조회수 증가")
    @GetMapping("/ledger/{id}")
    public LedgerResDto viewone(@PathVariable Long id) {
        return ledgerService.viewOne(id);
    }

    @Operation(summary = "ledger 수정", description = "성공하면 아래의 내용이 수정됨" + "\n\n" +
            "title"+ "\n\n" +
            "description"+ "\n\n" +
            "location"+ "\n\n" +
            "category"+ "\n\n" +
            "date")
    @PatchMapping("/ledger/{id}")
    public ResponseEntity<String> updateLedger(@RequestHeader(name = "accessToken") String accessToken,
                                               @PathVariable Long id,
                                               @RequestPart(value = "ledgerReqDto", required = false) LedgerReqDto ledgerReqDto,
                                               @RequestPart(value = "file", required = false)List<MultipartFile> files) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        ledgerService.update(id, member, ledgerReqDto, files);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Operation(summary = "ledger 삭제", description = "해당 id 값의 ledger 삭제")
    @DeleteMapping("ledger/{id}")
    public ResponseEntity<String> deleteLedger(@RequestHeader(name = "accessToken") String accessToken,
                                               @PathVariable Long id) {
        Member member = memberController.findMemberByToken(accessToken);
        ledgerService.delete(id, member);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @CrossOrigin
    @Operation(summary = "파일 id로 파일 상세 조회", description = "성공하면 File 데이터베이스에 저장되어있는 파일 출력")
    @GetMapping(
            value = "ledger/file/{id}",
            produces = {MediaType.ALL_VALUE}
    )
    public String getLedgerFile(@PathVariable Long id) throws IOException {
        return "file://"+ ledgerService.findFileByFileId(id);
    }

    @CrossOrigin
    @Operation(summary = "ledger id와 파일 명으로 파일 상세 조회", description = "성공하면 File 데이터베이스에 저장되어있는 ledger 파일 url 출력")
    @GetMapping(
            value = "ledger/{id}/file",
            produces = {MediaType.ALL_VALUE}
    )
    public String getLedgerFileByName(@PathVariable Long id,
                                      @RequestParam(name = "name") String fileName) throws IOException, URISyntaxException {

        return "file://"+ ledgerService.findFileByLedgerId(id, fileName);
    }

    @Operation(summary = "ledger 파일 다운로드", description = "성공하면 로컬에 자동 다운로드")
    @GetMapping("ledger/download/{id}")
    public ResponseEntity<Object> downloadLedgerFile(@PathVariable Long id) throws IOException {
        return ledgerService.download(id);
    }

    @Operation(summary = "에디터 이미지 저장", description = "성공하면 File 데이터베이스에 저장 + ledger 파일 url 출력")
    @PostMapping(value = "ledger/image", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Object> createImageUrl(@RequestHeader(name = "accessToken") String accessToken,
                                                 @RequestPart(value = "file", required = false)List<MultipartFile> imageFile) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        String path = "file://"+ ledgerService.createImageUrl(imageFile, member);
        return new ResponseEntity<>(path, HttpStatus.OK);
    }


}
