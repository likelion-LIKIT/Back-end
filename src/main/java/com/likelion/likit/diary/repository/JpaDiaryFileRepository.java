package com.likelion.likit.diary.repository;

import com.likelion.likit.diary.entity.Diary;
import com.likelion.likit.diary.entity.DiaryFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaDiaryFileRepository extends JpaRepository<DiaryFile, Long> {
    List<DiaryFile> findAllByDiaryIdAndIsThumbnail(Long diaryId, boolean isThumbnail);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE File c SET c.diary = :diary WHERE c.id = :id ")
    void updatediary(Diary diary, Long id);

    DiaryFile findByDiaryIdAndIsThumbnail(Long diaryId, boolean isThumbnail);
}
