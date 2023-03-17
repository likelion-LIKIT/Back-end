package com.likelion.likit.diary.repository;

import com.likelion.likit.diary.entity.Category;
import com.likelion.likit.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDiaryRepository extends JpaRepository<Diary, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Diary c SET c.title = :title WHERE c.id = :id ")
    void updateTitle(String title, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Diary c SET c.description = :description WHERE c.id = :id ")
    void updateDescription(String description, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Diary c SET c.location = :location WHERE c.id = :id ")
    void updateLocation(String location, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Diary c SET c.category = :category WHERE c.id = :id ")
    void updateCategory(Category category, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Diary c SET c.likes = :likes WHERE c.id = :id ")
    void updateLikes(Integer likes, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Diary c SET c.visit = :visit WHERE c.id = :id ")
    void updateVisit(int visit, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Diary c SET c.date = :date WHERE c.id = :id ")
    void updateDate(String date, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Diary c SET c.updateDate = :updateDate WHERE c.id = :id ")
    void updateUpdateDate(String updateDate, Long id);
}
