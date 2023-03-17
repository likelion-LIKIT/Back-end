package com.likelion.likit.diary;

import com.likelion.likit.diary.dto.DiaryReqDto;
import com.likelion.likit.diary.dto.DiaryThumbnailDto;
import com.likelion.likit.diary.entity.Diary;
import com.likelion.likit.member.MemberController;
import com.likelion.likit.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Integer> create(@RequestHeader String accessToken,
                                              @RequestPart(value = "diaryReqDto") DiaryReqDto diaryReqDto,
                                              @RequestPart(value = "thumbnail")List<MultipartFile> thumbnail,
                                              @RequestPart(value = "file", required = false)List<MultipartFile> files) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        diaryService.create(member, diaryReqDto, thumbnail, files);
        return ResponseEntity.ok(1);
    }

    @Operation(summary = "diary 글 조회", description = "Diary 글 조희")
    @GetMapping("/diary")
    public List<Diary> viewDiary() {
        return diaryService.viewDiary();
    }

    @Operation(summary = "diary 글 조회", description = "Diary 글 조희")
    @GetMapping("/diary/thubnail")
    public List<DiaryThumbnailDto> viewDiaryWithThubnail() {
        return diaryService.viewDiaryWithThumbnail();
    }


}
