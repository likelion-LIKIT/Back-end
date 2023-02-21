package com.likelion.likit.member.repository;

import com.likelion.likit.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaMemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByStudentId(String studentId);

}
