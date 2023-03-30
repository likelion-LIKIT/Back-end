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


}
