package com.likelion.likit.homework.repository;

import com.likelion.likit.homework.entity.Homework;
import com.likelion.likit.homework.entity.HomeworkLikeMembers;
import com.likelion.likit.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaHomeworkLikeRepository extends JpaRepository<HomeworkLikeMembers, Long> {
    List<HomeworkLikeMembers> findByHomeworkId(Long id);

    Optional<HomeworkLikeMembers> findByHomeworkAndMember(Homework homework, Member member);
}
