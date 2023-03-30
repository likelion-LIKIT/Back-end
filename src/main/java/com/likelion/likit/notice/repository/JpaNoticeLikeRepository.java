package com.likelion.likit.notice.repository;

import com.likelion.likit.notice.entity.Notice;
import com.likelion.likit.notice.entity.NoticeLikeMembers;
import com.likelion.likit.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaNoticeLikeRepository extends JpaRepository<NoticeLikeMembers, Long> {
    List<NoticeLikeMembers> findByNoticeId(Long id);

    Optional<NoticeLikeMembers> findByNoticeAndMember(Notice notice, Member member);
}
