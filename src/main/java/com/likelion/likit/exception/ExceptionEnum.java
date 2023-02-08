package com.likelion.likit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionEnum {
    StudentIdNotMatched(HttpStatus.UNAUTHORIZED,1000,"해당 학번의 회원이 존재하지 않습니다."),
    TokenMalformed(HttpStatus.UNAUTHORIZED,1001,"지원하지 않는 형태의 토큰이 입력되었습니다."),
    SecurityInvalidToken(HttpStatus.UNAUTHORIZED,1002,"Access Token이 만료되었습니다."),
    NeedSignInAgain(HttpStatus.UNAUTHORIZED,1003,"세션이 만료되었습니다."),
    PasswordNotMatched(HttpStatus.UNAUTHORIZED,1004,"비밀번호가 일치하지 않습니다.");

    private HttpStatus status;
    private int code;
    private String description;

    private ExceptionEnum(HttpStatus status,int code, String description){
        this.code=code;
        this.status=status;
        this.description=description;
    }
}
