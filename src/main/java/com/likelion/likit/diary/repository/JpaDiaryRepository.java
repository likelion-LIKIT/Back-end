package com.likelion.likit.diary.repository;

import com.likelion.likit.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDiaryRepository extends JpaRepository<Diary, Long> {
}
