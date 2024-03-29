package com.likelion.likit.homework;

import com.likelion.likit.homework.dto.HomeworkReqDto;
import com.likelion.likit.homework.dto.HomeworkResDto;
import com.likelion.likit.homework.entity.Homework;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeworkController {

    private final MemberController memberController;
    private final HomeworkService homeworkService;

    @Operation(summary = "homework 글 작성", description = "성공하면 게시글이 Homework 데이터베이스에 저장")
    @PostMapping("/homework")
    public ResponseEntity<String> create(@RequestHeader String accessToken,
                                         @RequestPart(value = "homeworkReqDto") HomeworkReqDto homeworkReqDto,
                                         @RequestPart(value = "file", required = false)List<MultipartFile> files) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        homeworkService.create(member, homeworkReqDto, files);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Operation(summary = "homework 글 조회", description = "Homework 글 조희")
    @GetMapping("/homework")
    public List<Homework> viewHomework(@RequestParam(name = "temp", defaultValue = "false", required = false) boolean temp ) {
        return homeworkService.viewHomework(temp);
    }

    @Operation(summary = "homework 글 정보 모두 조회", description = "Homework 글 조희")
    @GetMapping("/homework/all")
    public List<HomeworkResDto> viewAll() {
        return homeworkService.view();
    }

    @Operation(summary = "homework id 글 정보 모두 조회", description = "해당 Homework 글 조희 + 조회수 증가")
    @GetMapping("/homework/{id}")
    public HomeworkResDto viewone(@PathVariable Long id) {
        return homeworkService.viewOne(id);
    }

    @Operation(summary = "homework 수정", description = "성공하면 아래의 내용이 수정됨" + "\n\n" +
            "title"+ "\n\n" +
            "description"+ "\n\n" +
            "location"+ "\n\n" +
            "category"+ "\n\n" +
            "date")
    @PatchMapping("/homework/{id}")
    public ResponseEntity<String> updateHomework(@RequestHeader(name = "accessToken") String accessToken,
                                                 @PathVariable Long id,
                                                 @RequestPart(value = "homeworkReqDto", required = false) HomeworkReqDto homeworkReqDto,
                                                 @RequestPart(value = "file", required = false)List<MultipartFile> files) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        homeworkService.update(id, member, homeworkReqDto, files);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Operation(summary = "homework 삭제", description = "해당 id 값의 homework 삭제")
    @DeleteMapping("homework/{id}")
    public ResponseEntity<String> deleteHomework(@RequestHeader(name = "accessToken") String accessToken,
                                                 @PathVariable Long id) {
        Member member = memberController.findMemberByToken(accessToken);
        homeworkService.delete(id, member);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @Operation(summary = "homework 좋아요 등록 및 취소", description =  "해당 글 좋아요 누른 적이 없으면 해당 글에 좋아요 등록 및 좋아요 수 Up &"+ "\n\n" +
            " 해당 글에 좋아요 누른 적이 있으면 해당 글에 좋아요 취소 및 좋아요 수 Down")
    @PostMapping("homework/{id}/like")
    public ResponseEntity<String> likeHomework(@RequestHeader(name = "accessToken") String accessToken,
                                               @PathVariable Long id) {
        Member member = memberController.findMemberByToken(accessToken);
        String result = homeworkService.like(id, member) + " Success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "homework 좋아요 등록 여부 확인", description = "해당 USER가 좋아요를 눌렀는지 확인 여부"+"\n\n"+
            "해당 USER가 좋아요 리스트에 존재하면 \"LIKED\"" +"\n\n"+
            "해당 USER가 좋아요 리스트에 존재하지 않으면 \"UNLIKED\""+"\n\n")
    @GetMapping("homework/{id}/check")
    public ResponseEntity<String> checkLiked(@RequestHeader(name = "accessToken") String accessToken,
                                             @PathVariable Long id) {
        Member member = memberController.findMemberByToken(accessToken);

        return new ResponseEntity<>(homeworkService.checkLike(id, member), HttpStatus.OK);
    }

    @Operation(summary = "해당 Homework의 좋아요 누른 회원리스트", description = "해당 Homework의 좋아요 누른 회원 이름 반환")
    @GetMapping("homework/{id}/like")
    public ResponseEntity<List<String>> homeworkLikeList(@PathVariable Long id) {
        return new ResponseEntity<>(homeworkService.likeList(id), HttpStatus.OK);
    }

    @CrossOrigin
    @Operation(summary = "파일 id로 파일 상세 조회", description = "성공하면 File 데이터베이스에 저장되어있는 파일 출력")
    @GetMapping(
            value = "homework/file/{id}",
            produces = {MediaType.ALL_VALUE}
    )
    public ResponseEntity<byte[]> getHomeworkFile(@PathVariable Long id) throws IOException {
        String path = homeworkService.findFileByFileId(id);
        InputStream imageStream = new FileInputStream(path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }

    @CrossOrigin
    @Operation(summary = "homework id와 파일 명으로 파일 상세 조회", description = "성공하면 File 데이터베이스에 저장되어있는 homework 파일 url 출력")
    @GetMapping(
            value = "homework/{id}/file",
            produces = {MediaType.ALL_VALUE}
    )
    public ResponseEntity<byte[]> getHomeworkFileByName(@PathVariable Long id,
                                        @RequestParam(name = "name") String fileName) throws IOException, URISyntaxException {

        String path = homeworkService.findFileByHomeworkId(id, fileName);
        InputStream imageStream = new FileInputStream(path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }

    @Operation(summary = "homework 파일 다운로드", description = "성공하면 로컬에 자동 다운로드")
    @GetMapping("homework/download/{id}")
    public ResponseEntity<Object> downloadHomeworkFile(@PathVariable Long id) throws IOException {
        return homeworkService.download(id);
    }

    @Operation(summary = "에디터 이미지 저장", description = "성공하면 File 데이터베이스에 저장 + homework 파일 url 출력")
    @PostMapping(value = "homework/image", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> createImageUrl(@RequestHeader(name = "accessToken") String accessToken,
                                                 @RequestPart(value = "file", required = false)List<MultipartFile> imageFile) throws Exception {
        Member member = memberController.findMemberByToken(accessToken);
        String path = homeworkService.createImageUrl(imageFile, member);
        InputStream imageStream = new FileInputStream(path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }
}
