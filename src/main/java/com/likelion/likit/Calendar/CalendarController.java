package com.likelion.likit.Calendar;

import com.likelion.likit.Calendar.dto.CalendarReqDto;
import com.likelion.likit.Calendar.entity.Calendar;
import com.likelion.likit.member.MemberController;
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
        memberController.findMemberByToken(accessToken);
        Calendar calendar = calendarReqDto.toEntity();
        calendarService.join(calendar);
    }

    @Operation(summary = "일정 전체 조회")
    @GetMapping("/calendar")
    public List<Calendar> viewCalendar(@RequestParam(name = "YY", required = false) Integer year,
                                       @RequestParam(name = "MM", required = false) Integer month,
                                       @RequestParam(name = "DD", required = false) Integer date) {
        return calendarService.viewCalendar(year, month, date);
    }
}
