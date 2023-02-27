package com.likelion.likit.Calendar;

import com.likelion.likit.Calendar.entity.Calendar;
import com.likelion.likit.Calendar.repository.JpaCalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Calendar> viewCalendar(){
        return jpaCalendarRepository.findAll();
    }
}
