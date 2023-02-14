package com.likelion.likit.member.entity;

import com.likelion.likit.token.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member implements UserDetails{
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String studentId;
    private String password;

    @Column(name = "joined_date") @CreatedDate
    private String joinedDate;

    @Column(name = "last_login_date") @CreatedDate
    private String lastLoginDate;

    private Boolean isActive = true;
    private Boolean isStaff = false;
    private Boolean isSuperuser = false;

    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE)
    private MemberDetail memberDetails;

    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE)
    private Token token;


    @Builder
    public Member(String studentId, String password,
                  Boolean isActive, Boolean isStaff, Boolean isSuperuser, MemberDetail memberDetails) {
        this.studentId = studentId;
        this.password = password;
        this.joinedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
        this.lastLoginDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy.MM.dd HH:mm"));
        this.isActive = isActive;
        this.isStaff = isStaff;
        this.isSuperuser = isSuperuser;
        this.memberDetails = memberDetails;
    }

    @Override
    // 원래 해당 레벨에서 여러 개의 인증 권한을 가질 수 있는 경우를 위해 Collection을 사용
    // 우선 그런 경우를 상정하지 않고 TokenService에서 직접 해당 객체를 만들어 사용하는 것으로 대체
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.studentId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
