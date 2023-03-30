package com.likelion.likit.homework.repository;

import com.likelion.likit.homework.entity.Category;
import com.likelion.likit.homework.entity.Homework;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaHomeworkRepository extends JpaRepository<Homework, Long> {
    List<Homework> findByTempTrue(Sort sort);
    List<Homework> findByTempFalse(Sort sort);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Homework c SET c.title = :title WHERE c.id = :id ")
    void updateTitle(String title, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Homework c SET c.description = :description WHERE c.id = :id ")
    void updateDescription(String description, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Homework c SET c.location = :location WHERE c.id = :id ")
    void updateLocation(String location, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Homework c SET c.category = :category WHERE c.id = :id ")
    void updateCategory(Category category, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Homework c SET c.likes = :likes WHERE c.id = :id ")
    void updateLikes(Integer likes, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Homework c SET c.visit = :visit WHERE c.id = :id ")
    void updateVisit(int visit, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Homework c SET c.date = :date WHERE c.id = :id ")
    void updateDate(String date, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Homework c SET c.updateDate = :updateDate WHERE c.id = :id ")
    void updateUpdateDate(String updateDate, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Homework c SET c.temp = :temp WHERE c.id = :id ")
    void updateTemp(boolean temp, Long id);
}
