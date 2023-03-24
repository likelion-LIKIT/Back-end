package com.likelion.likit.activity.repository;

import com.likelion.likit.activity.entity.Activity;
import com.likelion.likit.activity.entity.ProjectJoin;
import com.likelion.likit.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaProjectJoin extends JpaRepository<ProjectJoin, Long> {
    List<ProjectJoin> findByActivityId(Long id);

    Optional<ProjectJoin> findByActivityAndMember(Activity activity, Member member);
}
