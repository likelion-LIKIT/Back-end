package com.likelion.likit.notice;

import com.likelion.likit.notice.dto.NoticeReqDto;
import com.likelion.likit.notice.dto.NoticeResDto;
import com.likelion.likit.notice.dto.NoticeThumbnailDto;
import com.likelion.likit.notice.entity.Notice;
import com.likelion.likit.file.FileDto;
import com.likelion.likit.member.MemberController;
import com.likelion.likit.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final MemberController memberController;
    private final NoticeService noticeService;

    @Operation(summary = "notice 글 작성", description = "성공하면 게시글이 Notice 데이터베이스에 저장")
    @PostMapping("/notice")
    public ResponseEntity<String> create(@RequestHeader String accessToken,
                                         @RequestPart(value = "noticeReqDto") NoticeReqDto noticeReqDto,
                                         @RequestPart(value = "thumbnail")List<MultipartFile> thumbnail,
                                         @RequestPart(value = "file", required = false)List<MultipartFile> files) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        noticeService.create(member, noticeReqDto, thumbnail, files);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    @Operation(summary = "notice 글 조회", description = "Notice 글 조희")
    @GetMapping("/notice")
    public List<Notice> viewNotice() {
        return noticeService.viewNotice();
    }

    @Operation(summary = "thumbnail과 함께 notice 글 조회", description = "Notice 글 조희")
    @GetMapping("/notice/thumbnail")
    public List<NoticeThumbnailDto> viewNoticeWithThumbnail() {
        return noticeService.viewNoticeWithThumbnail();
    }

    @Operation(summary = "notice 글 정보 모두 조회", description = "Notice 글 조희")
    @GetMapping("/notice/all")
    public List<NoticeResDto> viewAll() {
        return noticeService.view();
    }

    @Operation(summary = "notice id 글 정보 모두 조회", description = "해당 Notice 글 조희 + 조회수 증가")
    @GetMapping("/notice/{id}")
    public NoticeResDto viewone(@PathVariable Long id) {
        return noticeService.viewOne(id);
    }

}
