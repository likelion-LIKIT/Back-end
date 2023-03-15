package com.likelion.likit.diary;

import com.likelion.likit.diary.dto.DiaryReqDto;
import com.likelion.likit.diary.dto.DiaryResDto;
import com.likelion.likit.diary.entity.Diary;
import com.likelion.likit.member.MemberController;
import com.likelion.likit.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final MemberController memberController;
    private final DiaryService diaryService;

    @Operation(summary = "diary 글 작성", description = "성공하면 게시글이 Diary 데이터베이스에 저장")
    @PostMapping("/diary")
    public ResponseEntity<DiaryResDto> create(@RequestHeader String accessToken,
                                              @RequestPart(value = "diaryReqDto") DiaryReqDto diaryReqDto,
                                              @RequestPart(value = "file", required = false)List<MultipartFile> files) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        return ResponseEntity.ok(diaryService.create(member, diaryReqDto, files));
    }

    @Operation(summary = "diary 글 조회", description = "Diary 글 조희")
    @GetMapping("/diary")
    public List<Diary> viewDiary() {
        return diaryService.viewDiary();
    }

//    @Operation(summary = "diary 글 작성", description = "성공하면 게시글이 Diary 데이터베이스에 저장")
//    @PostMapping("/diary")
//    public ResponseEntity<DiaryResDto> create(@RequestHeader String accessToken,
//                                              @RequestPart(value = "diaryReqDto") DiaryReqDto diaryReqDto,
//                                              @RequestPart(value = "file", required = false)List<MultipartFile> files) throws Exception {
//        Member member = memberController.findMemberByToken(accessToken);
//        return ResponseEntity.ok(diaryService.create(member, diaryReqDto, files));
//    }
}
