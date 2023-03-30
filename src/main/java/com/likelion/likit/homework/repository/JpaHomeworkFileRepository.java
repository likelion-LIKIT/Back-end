package com.likelion.likit.homework.repository;

import com.likelion.likit.homework.entity.Homework;
import com.likelion.likit.homework.entity.HomeworkFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaHomeworkFileRepository extends JpaRepository<HomeworkFile, Long> {
    List<HomeworkFile> findAllByHomeworkId(Long homeworkId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE HomeworkFile c SET c.homework = :homework WHERE c.id = :id ")
    void updateHomework(Homework homework, Long id);

}
