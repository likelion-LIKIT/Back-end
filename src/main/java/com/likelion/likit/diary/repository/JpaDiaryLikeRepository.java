package com.likelion.likit.diary.repository;

import com.likelion.likit.diary.entity.Diary;
import com.likelion.likit.diary.entity.LikeMembers;
import com.likelion.likit.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaDiaryLikeRepository extends JpaRepository<LikeMembers, Long> {
    List<LikeMembers> findByDiaryId(Long id);

    Optional<LikeMembers> findByDiaryAndMember(Diary diary, Member member);
}
