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
import java.net.URISyntaxException;
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

    @Operation(summary = "notice 수정", description = "성공하면 아래의 내용이 수정됨" + "\n\n" +
            "title"+ "\n\n" +
            "description"+ "\n\n" +
            "location"+ "\n\n" +
            "category"+ "\n\n" +
            "date")
    @PatchMapping("/notice/{id}")
    public ResponseEntity<String> updateNotice(@RequestHeader(name = "accessToken") String accessToken,
                                               @PathVariable Long id,
                                               @RequestPart(value = "noticeReqDto", required = false) NoticeReqDto noticeReqDto,
                                               @RequestPart(value = "thumbnail", required = false)List<MultipartFile> thumbnail,
                                               @RequestPart(value = "file", required = false)List<MultipartFile> files) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        noticeService.update(id, member, noticeReqDto, thumbnail, files);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Operation(summary = "notice 삭제", description = "해당 id 값의 notice 삭제")
    @DeleteMapping("notice/{id}")
    public ResponseEntity<String> deleteNotice(@RequestHeader(name = "accessToken") String accessToken,
                                               @PathVariable Long id) {
        Member member = memberController.findMemberByToken(accessToken);
        noticeService.delete(id, member);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Operation(summary = "notice 좋아요 등록 및 취소", description =  "해당 글 좋아요 누른 적이 없으면 해당 글에 좋아요 등록 및 좋아요 수 Up &"+ "\n\n" +
            " 해당 글에 좋아요 누른 적이 있으면 해당 글에 좋아요 취소 및 좋아요 수 Down")
    @PostMapping("notice/{id}/like")
    public ResponseEntity<String> likeNotice(@RequestHeader(name = "accessToken") String accessToken,
                                             @PathVariable Long id) {
        Member member = memberController.findMemberByToken(accessToken);
        String result = noticeService.like(id, member) + " Success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "notice 좋아요 등록 여부 확인", description = "해당 USER가 좋아요를 눌렀는지 확인 여부"+"\n\n"+
            "해당 USER가 좋아요 리스트에 존재하면 \"LIKED\"" +"\n\n"+
            "해당 USER가 좋아요 리스트에 존재하지 않으면 \"UNLIKED\""+"\n\n")
    @GetMapping("notice/{id}/check")
    public ResponseEntity<String> checkLiked(@RequestHeader(name = "accessToken") String accessToken,
                                             @PathVariable Long id) {
        Member member = memberController.findMemberByToken(accessToken);

        return new ResponseEntity<>(noticeService.checkLike(id, member), HttpStatus.OK);
    }

    @CrossOrigin
    @Operation(summary = "notice 파일 상세 조회", description = "성공하면 File 데이터베이스에 저장되어있는 notice 파일 출력")
    @GetMapping(
            value = "notice/file/{id}",
            produces = {MediaType.ALL_VALUE}
    )
    public String getNoticeFile(@PathVariable Long id) throws IOException {
        String path = "file://" + noticeService.findFileByFileId(id);

        return path;
    }

    @CrossOrigin
    @Operation(summary = "notice id와 파일 명으로 파일 상세 조회", description = "성공하면 File 데이터베이스에 저장되어있는 notice 파일 url 출력")
    @GetMapping(
            value = "notice/{id}/file",
            produces = {MediaType.ALL_VALUE}
    )
    public String getnoticeFileByName(@PathVariable Long id,
                                     @RequestParam(name = "name") String fileName) throws IOException, URISyntaxException {

//        Path path = Paths.get(diaryService.findDiaryByDiaryId(id, fileName));
//        String file = path;
//        RedirectView redirectView = new RedirectView();
//        redirectView.setUrl("file://"+diaryService.findDiaryByDiaryId(id, fileName));
//        System.out.println(redirectView);
        return "file://"+ noticeService.findFileByFileName(id, fileName);
    }

    @Operation(summary = "notice 파일 다운로드", description = "성공하면 로컬에 자동 다운로드")
    @GetMapping("notice/download/{id}")
    public ResponseEntity<Object> downloadNoticeFile(@PathVariable Long id) throws IOException {
        return noticeService.download(id);
    }

}
