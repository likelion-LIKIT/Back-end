package com.likelion.likit.Calendar.repository;

import com.likelion.likit.Calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCalendarRepository extends JpaRepository<Calendar, Long> {
}
