package com.likelion.likit.token;

import com.likelion.likit.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTokenRepository extends JpaRepository<Token,Long> {
    Token findByMember(Member member);
    Token findByRefreshToken(String refreshToken);
}

