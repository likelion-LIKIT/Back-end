package com.likelion.likit.homework;

import com.likelion.likit.homework.dto.HomeworkReqDto;
import com.likelion.likit.homework.dto.HomeworkResDto;
import com.likelion.likit.homework.entity.Homework;
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


}
