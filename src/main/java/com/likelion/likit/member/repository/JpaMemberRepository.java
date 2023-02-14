package com.likelion.likit.member.repository;

import com.likelion.likit.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByStudentId(String studentId);

}
