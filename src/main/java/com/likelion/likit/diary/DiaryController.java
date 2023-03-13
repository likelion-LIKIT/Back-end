package com.likelion.likit.diary;

import com.likelion.likit.diary.dto.DiaryReqDto;
import com.likelion.likit.diary.dto.DiaryResDto;
import com.likelion.likit.member.MemberController;
import com.likelion.likit.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
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
}
