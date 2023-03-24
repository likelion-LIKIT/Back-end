package com.likelion.likit.activity.repository;

import com.likelion.likit.activity.entity.Activity;
import com.likelion.likit.activity.entity.ActivityURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaActivityURLRepository extends JpaRepository<ActivityURL, Long> {
    List<ActivityURL> findAllByActivityId(Long activityId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ActivityURL c SET c.activity = :activity WHERE c.id = :id ")
    void updateActivity(Activity activity, Long id);
}
