package com.likelion.likit.notice.repository;

import com.likelion.likit.notice.entity.Notice;
import com.likelion.likit.notice.entity.NoticeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaNoticeFileRepository extends JpaRepository<NoticeFile, Long> {
    List<NoticeFile> findAllByNoticeIdAndIsThumbnail(Long noticeId, boolean isThumbnail);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE NoticeFile c SET c.notice = :notice WHERE c.id = :id ")
    void updateNotice(Notice notice, Long id);

    NoticeFile findByNoticeIdAndIsThumbnail(Long noticeId, boolean isThumbnail);

    List<NoticeFile> findAllByNoticeId(Long id);

}
