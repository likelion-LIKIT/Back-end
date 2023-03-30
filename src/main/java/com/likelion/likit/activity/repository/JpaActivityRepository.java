package com.likelion.likit.activity.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaActivityRepository {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Activity c SET c.title = :title WHERE c.id = :id ")
    void updateTitle(String title, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Activity c SET c.description = :description WHERE c.id = :id ")
    void updateDescription(String description, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Activity c SET c.start_date = :start_date WHERE c.id = :id ")
    void updateStartDate(String startDate, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Activity c SET c.end_date = :end_date WHERE c.id = :id ")
    void updateEndDate(String endDate, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Activity c SET c.updateDate = :updateDate WHERE c.id = :id ")
    void updateUpdateDate(String updateDate, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Activity c SET c.achieve = :achieve WHERE c.id = :id ")
    void updateAchieve(String achieve, Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Activity c SET c.github = :github WHERE c.id = :id ")
    void updateGithub(String github, Long id);
}
