package com.likelion.likit.member.repository;

import com.likelion.likit.member.entity.MemberTechStack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberTechStackRepository extends JpaRepository<MemberTechStack , Long> {
}
