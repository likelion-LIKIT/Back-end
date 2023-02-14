package com.likelion.likit.member.repository;

import com.likelion.likit.member.entity.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaTechStackRepository extends JpaRepository<TechStack , Long> {

    TechStack findByTechStack(String techStack);
}
