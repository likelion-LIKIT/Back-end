package com.likelion.likit.Calendar;

import com.likelion.likit.Calendar.entity.Calendar;
import com.likelion.likit.Calendar.repository.JpaCalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {
    private final JpaCalendarRepository jpaCalendarRepository;

    @Transactional
    public void join(Calendar calendar) {
        jpaCalendarRepository.save(calendar);
    }

    public List<Calendar> viewCalendar(Integer year, Integer month, Integer day) {
        List<Calendar> sortDate = jpaCalendarRepository.findAll(Sort.by(Sort.Direction.ASC, "dateTime"));
        List<Calendar> sortYear = sortDate;
        List<Calendar> sortMonth;
        List<Calendar> sortDay;

        if (year != null)
            sortYear = sortDate.stream().filter(c -> c.getDateTime().getYear() == year).collect(Collectors.toList());
        if (month != null)
            sortMonth = sortYear.stream().filter(c -> c.getDateTime().getMonthValue() == month).collect(Collectors.toList());
        else sortMonth = sortYear;
        if (day != null)
            sortDay = sortMonth.stream().filter(c -> c.getDateTime().getDayOfMonth() == day).collect(Collectors.toList());
        else sortDay = sortMonth;

        return sortDay;
    }

}
