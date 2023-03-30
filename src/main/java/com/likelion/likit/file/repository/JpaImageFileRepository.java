package com.likelion.likit.file.repository;

import com.likelion.likit.file.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaImageFileRepository extends JpaRepository<ImageFile, Long> {

}
