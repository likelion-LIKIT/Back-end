package com.likelion.likit.token;

import com.likelion.likit.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTokenRepository extends JpaRepository<Token,Long> {
    Token findByMember(Member member);
    Token findByRefreshToken(String refreshToken);
}

