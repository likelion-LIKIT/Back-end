package com.likelion.likit.activity.repository;

import com.likelion.likit.activity.entity.Activity;
import com.likelion.likit.activity.entity.ActivityFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaActivityFileRepository extends JpaRepository<ActivityFile, Long> {
    List<ActivityFile> findAllByActivityAndIsDifference(Long activityId, int isDifference);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ActivityFile c SET c.activity = :activity WHERE c.id = :id ")
    void updateActivity(Activity activity, Long id);

    ActivityFile findByActivityIdAndIsDifference(Long activityId, int isDifference);
}
