package com.likelion.likit.diary;

import com.likelion.likit.diary.dto.DiaryReqDto;
import com.likelion.likit.diary.dto.DiaryResDto;
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

    @Operation(summary = "thumbnail과 함께 diary 글 조회", description = "Diary 글 조희")
    @GetMapping("/diary/thumbnail")
    public List<DiaryThumbnailDto> viewDiaryWithThubnail() {
        return diaryService.viewDiaryWithThumbnail();
    }

    @Operation(summary = "diary 글 정보 모두 조회", description = "Diary 글 조희")
    @GetMapping("/diary/all")
    public List<DiaryResDto> viewAll() {
        return diaryService.view();
    }

    @Operation(summary = "diary id 글 정보 모두 조회", description = "해당 Diary 글 조희 + 조회수 증가")
    @GetMapping("/diary/{id}")
    public DiaryResDto viewone(@PathVariable Long id) {
        return diaryService.viewOne(id);
    }

    @Operation(summary = "diary 수정", description = "성공하면 아래의 내용이 수정됨" + "\n\n" +
            "title"+ "\n\n" +
            "description"+ "\n\n" +
            "location"+ "\n\n" +
            "category"+ "\n\n" +
            "date")
    @PatchMapping("/diary/{id}")
    public ResponseEntity<DiaryResDto> updateBoard(@RequestHeader(name = "accessToken") String accessToken,
                                                   @PathVariable Long id,
                                                   @RequestPart(value = "diaryReqDto", required = false) DiaryReqDto diaryReqDto,
                                                   @RequestPart(value = "thumbnail", required = false)List<MultipartFile> thumbnail,
                                                   @RequestPart(value = "file", required = false)List<MultipartFile> files) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        return ResponseEntity.ok(diaryService.update(id, member, diaryReqDto, thumbnail, files));
    }



}
