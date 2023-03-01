package com.likelion.likit.calendar.repository;

import com.likelion.likit.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCalendarRepository extends JpaRepository<Calendar, Long> {
}
