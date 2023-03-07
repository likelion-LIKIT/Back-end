package com.likelion.likit.file.repository;

import com.likelion.likit.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFileRepository extends JpaRepository<File, Long> {
}
