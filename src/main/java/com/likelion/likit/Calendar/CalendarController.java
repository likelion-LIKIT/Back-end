package com.likelion.likit.Calendar;

import com.likelion.likit.Calendar.dto.CalendarReqDto;
import com.likelion.likit.Calendar.entity.Calendar;
import com.likelion.likit.exception.CustomException;
import com.likelion.likit.exception.ExceptionEnum;
import com.likelion.likit.member.MemberController;
import com.likelion.likit.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalendarController {
    private final MemberController memberController;
    private final CalendarService calendarService;


    @Operation(summary = "일정 등록", description = "성공하면 OK 반환")
    @PostMapping("/calendar")
    public void createCalendar(@RequestHeader String accessToken,
                                 @RequestBody CalendarReqDto calendarReqDto) {
        Member member = memberController.findMemberByToken(accessToken);
        if (member.getIsStaff()){
            Calendar calendar = calendarReqDto.toEntity();
            calendarService.join(calendar);
        } else {
            throw new CustomException(ExceptionEnum.UNAUTHORIZED);
        }

    }

    @Operation(summary = "일정 전체 시간 순 조회", description = "년도, 월, 일 각자 따로 조회 가능 \n\n " +
            "ex)\n\n  ⚫ 전체 조회 : /calendar\n\n  ⚫ 연도 조회 : /calendar?YY=2023\n\n " +
            "⚫ 월 조회 : /calendar?MM=2\n\n ⚫ 일 조회 /calendar?DD=12\n\n " +
            "⚫ 해당 연도의 월 조회 : /calendar?YY=2023&MM=2\n\n ⚫ 해당 연도의 해당 월의 일 조회 : /calendar?YY=2023&MM=2&DD=12\n\n  등")
    @GetMapping("/calendar")
    public List<Calendar> viewCalendar(@RequestParam(name = "YY", required = false) Integer year,
                                       @RequestParam(name = "MM", required = false) Integer month,
                                       @RequestParam(name = "DD", required = false) Integer date) {
        return calendarService.viewCalendar(year, month, date);
    }

    @Operation(summary = "일정 삭제")
    @DeleteMapping("/calendar/{id}")
    public void deleteCalendar(@RequestHeader String accessToken,
                               @PathVariable Long id) {
        Member member = memberController.findMemberByToken(accessToken);
        if (member.getIsStaff()){
            calendarService.delete(id);
        } else {
            throw new CustomException(ExceptionEnum.UNAUTHORIZED);
        }

    }
}
