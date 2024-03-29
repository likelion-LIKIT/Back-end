package com.likelion.likit.notice.repository;

import com.likelion.likit.notice.entity.Category;
import com.likelion.likit.notice.entity.Notice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaNoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByTempTrue(Sort sort);
    List<Notice> findByTempFalse(Sort sort);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notice c SET c.title = :title WHERE c.id = :id ")
    void updateTitle(String title, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notice c SET c.description = :description WHERE c.id = :id ")
    void updateDescription(String description, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notice c SET c.location = :location WHERE c.id = :id ")
    void updateLocation(String location, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notice c SET c.category = :category WHERE c.id = :id ")
    void updateCategory(Category category, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notice c SET c.likes = :likes WHERE c.id = :id ")
    void updateLikes(Integer likes, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notice c SET c.visit = :visit WHERE c.id = :id ")
    void updateVisit(int visit, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notice c SET c.date = :date WHERE c.id = :id ")
    void updateDate(String date, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notice c SET c.updateDate = :updateDate WHERE c.id = :id ")
    void updateUpdateDate(String updateDate, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notice c SET c.temp = :temp WHERE c.id = :id ")
    void updateTemp(boolean temp, Long id);
}
