package com.likelion.likit.diary;

import com.likelion.likit.diary.dto.DiaryReqDto;
import com.likelion.likit.diary.dto.DiaryResDto;
import com.likelion.likit.diary.dto.DiaryThumbnailDto;
import com.likelion.likit.diary.entity.Diary;
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
public class DiaryController {

    private final MemberController memberController;
    private final DiaryService diaryService;

    @Operation(summary = "diary 글 작성", description = "성공하면 게시글이 Diary 데이터베이스에 저장")
    @PostMapping("/diary")
    public ResponseEntity<String> create(@RequestHeader String accessToken,
                                         @RequestPart(value = "diaryReqDto") DiaryReqDto diaryReqDto,
                                         @RequestPart(value = "thumbnail")List<MultipartFile> thumbnail,
                                         @RequestPart(value = "file", required = false)List<MultipartFile> files) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        diaryService.create(member, diaryReqDto, thumbnail, files);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Operation(summary = "diary 글 조회", description = "Diary 글 조희")
    @GetMapping("/diary")
    public List<Diary> viewDiary() {
        return diaryService.viewDiary();
    }

    @Operation(summary = "thumbnail과 함께 diary 글 조회", description = "Diary 글 조희 (temp = false 이면 게시된 게시물, true이면 임시저장 글")
    @GetMapping("/diary/thumbnail")
    public List<DiaryThumbnailDto> viewDiaryWithThubnail(@RequestParam(name = "temp", defaultValue = "false", required = false) boolean temp ) {
        return diaryService.viewDiaryWithThumbnail(temp);
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
    public ResponseEntity<String> updateDiary(@RequestHeader(name = "accessToken") String accessToken,
                                              @PathVariable Long id,
                                              @RequestPart(value = "diaryReqDto", required = false) DiaryReqDto diaryReqDto,
                                              @RequestPart(value = "thumbnail", required = false)List<MultipartFile> thumbnail,
                                              @RequestPart(value = "file", required = false)List<MultipartFile> files) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        diaryService.update(id, member, diaryReqDto, thumbnail, files);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Operation(summary = "diary 삭제", description = "해당 id 값의 diary 삭제")
    @DeleteMapping("diary/{id}")
    public ResponseEntity<String> deleteDiary(@RequestHeader(name = "accessToken") String accessToken,
                                              @PathVariable Long id) {
        Member member = memberController.findMemberByToken(accessToken);
        diaryService.delete(id, member);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Operation(summary = "diary 좋아요 등록 및 취소", description =  "해당 글 좋아요 누른 적이 없으면 해당 글에 좋아요 등록 및 좋아요 수 Up &"+ "\n\n" +
            " 해당 글에 좋아요 누른 적이 있으면 해당 글에 좋아요 취소 및 좋아요 수 Down")
    @PostMapping("diary/{id}/like")
    public ResponseEntity<String> likeDiary(@RequestHeader(name = "accessToken") String accessToken,
                                            @PathVariable Long id) {
        Member member = memberController.findMemberByToken(accessToken);
        String result = diaryService.like(id, member) + " Success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "diary 좋아요 등록 여부 확인", description = "해당 USER가 좋아요를 눌렀는지 확인 여부"+"\n\n"+
            "해당 USER가 좋아요 리스트에 존재하면 \"LIKED\"" +"\n\n"+
            "해당 USER가 좋아요 리스트에 존재하지 않으면 \"UNLIKED\""+"\n\n")
    @GetMapping("diary/{id}/check")
    public ResponseEntity<String> checkLiked(@RequestHeader(name = "accessToken") String accessToken,
                                             @PathVariable Long id) {
        Member member = memberController.findMemberByToken(accessToken);

        return new ResponseEntity<>(diaryService.checkLike(id, member), HttpStatus.OK);
    }


    @Operation(summary = "해당 diary의 좋아요 누른 회원리스트", description = "해당 diary의 좋아요 누른 회원 이름 반환")
    @GetMapping("diary/{id}/like")
    public ResponseEntity<List<String>> diaryLikeList(@PathVariable Long id) {
        return new ResponseEntity<>(diaryService.likeList(id), HttpStatus.OK);
    }

    @CrossOrigin
    @Operation(summary = "파일 id로 파일 상세 조회", description = "성공하면 File 데이터베이스에 저장되어있는 파일 출력")
    @GetMapping(
            value = "diary/file/{id}",
            produces = {MediaType.ALL_VALUE}
    )
    public String getDiaryFile(@PathVariable Long id) throws IOException {
//        FileDto fileDto = diaryService.findFileByFileId(id);
//        String absolutePath
//                = new File("").getAbsolutePath() + File.separator + File.separator;
//        String path = fileDto.getFilePath();
//
//        InputStream imageStream = new FileInputStream(absolutePath + path);
//        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
//        imageStream.close();
//
//        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
        return "file://"+diaryService.findFileByFileId(id);
    }

    @CrossOrigin
    @Operation(summary = "diary id와 파일 명으로 파일 상세 조회", description = "성공하면 File 데이터베이스에 저장되어있는 diary 파일 url 출력")
    @GetMapping(
            value = "diary/{id}/file",
            produces = {MediaType.ALL_VALUE}
    )
    public String getDiaryFileByName(@PathVariable Long id,
                               @RequestParam(name = "name") String fileName) throws IOException, URISyntaxException {

//        Path path = Paths.get(diaryService.findDiaryByDiaryId(id, fileName));
//        String file = path;
//        RedirectView redirectView = new RedirectView();
//        redirectView.setUrl("file://"+diaryService.findDiaryByDiaryId(id, fileName));
//        System.out.println(redirectView);
        return "file://"+diaryService.findFileByDiaryId(id, fileName);
    }

    @Operation(summary = "diary 파일 다운로드", description = "성공하면 로컬에 자동 다운로드")
    @GetMapping("diary/download/{id}")
    public ResponseEntity<Object> downloadDiaryFile(@PathVariable Long id) throws IOException {
        return diaryService.download(id);
    }

    @Operation(summary = "에디터 이미지 저장", description = "성공하면 File 데이터베이스에 저장 + diary 파일 url 출력")
    @PostMapping(value = "diary/image", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Object> createImageUrl(@RequestHeader(name = "accessToken") String accessToken,
                              @RequestPart(value = "file", required = false)List<MultipartFile> imageFile) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        String path = "file://"+diaryService.createImageUrl(imageFile, member);
        return new ResponseEntity<>(path, HttpStatus.OK);
    }
}
