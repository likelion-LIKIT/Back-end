package com.likelion.likit.token;

import com.likelion.likit.exception.CustomException;
import com.likelion.likit.exception.ExceptionEnum;
import com.likelion.likit.member.MemberService;
import com.likelion.likit.member.entity.Member;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {
    private final JpaTokenRepository jpaTokenRepository;
    private final MemberService memberService;

    @Value("${key.token}")
    private String key;
    @Value("${validation.access}")
    private int accessValidTime;
    @Value("${validation.refresh}")
    private int refreshValidTime;

    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    public Token findTokenByMember(Member member) {
        return jpaTokenRepository.findByMember(member);
    }

    public void createToken(Token token) {
        join(token);
    }

    @Transactional
    public void join(Token token) {
        jpaTokenRepository.save(token);
    }

    public String makeAccessJws(Member member) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(Integer.valueOf(accessValidTime)).toMillis()))
                .claim("studentId", member.getUsername())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Authentication getAuthentication(String accessToken){
        List Authorizes = new ArrayList();
        String studentId = this.getStudentIdFromToken(accessToken);
        Member member = (Member) memberService.loadUserByUsername(studentId);
        // userDetails 클래스 형태의 Member 를 가져와서 형 변환 해준다.

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(member.getUsername());
        Authorizes.add(simpleGrantedAuthority);
        return new UsernamePasswordAuthenticationToken(member,"",Authorizes);
    }

    public String getStudentIdFromToken(String accessToken){
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(accessToken);
            return claims.getBody().get("studentId", String.class);
        }catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e){
            throw new CustomException(ExceptionEnum.TokenMalformed);
        }catch (ExpiredJwtException e){
            throw new CustomException(ExceptionEnum.SecurityInvalidToken);
        }
    }

    public boolean TokenCheck(String accessToken) {
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(accessToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e){
            throw new CustomException(ExceptionEnum.TokenMalformed);
        }catch (ExpiredJwtException e){
            return false;
        }
    }

    public String makeRefreshJws(){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(Integer.valueOf(refreshValidTime)).toMillis()))
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();
    }

    public boolean refreshTokenCheck(String refreshToken){
        return TokenCheck(refreshToken);
    }

    public String refreshProcess(String refreshToken) {
        Token token = jpaTokenRepository.findByRefreshToken(refreshToken);
        Member member = token.getMember();
        if(member==null){
            throw new CustomException(ExceptionEnum.StudentIdNotMatched);
        }else{
            if(refreshTokenCheck(refreshToken)){
                //access 만료, refresh 유효. access 재발급
                String newAccessToken = makeAccessJws(member);
                return newAccessToken;
            }else{
                // 둘 다 만료. 재 로그인 필요.
                throw new CustomException(ExceptionEnum.NeedSignInAgain);
            }
        }
    }

    @Transactional
    public TokenDto makeToken(Member member) {
        String accessJws = makeAccessJws(member);
        String refreshJws = makeRefreshJws();
        Token token = jpaTokenRepository.findByMember(member);
        token.updateToken(refreshJws);
        return new TokenDto(accessJws,refreshJws);
    }
}

