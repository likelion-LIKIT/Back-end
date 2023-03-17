package com.likelion.likit.file.repository;

import com.likelion.likit.diary.entity.Diary;
import com.likelion.likit.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaFileRepository extends JpaRepository<File, Long> {
    List<File> findAllByDiaryIdAndIsThumbnail(Long diaryId, boolean isThumbnail);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE File c SET c.diary = :diary WHERE c.id = :id ")
    void updatediary(Diary diary, Long id);

    File findByDiaryIdAndIsThumbnail(Long diaryId, boolean isThumbnail);
}
