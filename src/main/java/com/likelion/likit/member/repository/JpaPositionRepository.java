package com.likelion.likit.member.repository;

import com.likelion.likit.member.entity.Category;
import com.likelion.likit.member.entity.MemberDetail;
import com.likelion.likit.member.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JpaPositionRepository extends JpaRepository<Position,Long> {
    List<Position> findAllByMemberDetail(MemberDetail memberDetail);
    @Transactional
    @Modifying
    @Query("delete from Position c where c.id in :ids")
    void deleteAllByIdInQuery(List<Long> ids);
}
