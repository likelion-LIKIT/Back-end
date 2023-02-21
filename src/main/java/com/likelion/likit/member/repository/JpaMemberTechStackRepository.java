package com.likelion.likit.member.repository;

import com.likelion.likit.member.entity.MemberDetail;
import com.likelion.likit.member.entity.MemberTechStack;
import com.likelion.likit.member.entity.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JpaMemberTechStackRepository extends JpaRepository<MemberTechStack , Long> {

    List<MemberTechStack> findAllByMemberDetail(MemberDetail memberDetail);
    @Transactional
    @Modifying
    @Query("delete from MemberTechStack c where c.id in :ids")
    void deleteAllByIdInQuery(List<Long> ids);
}
