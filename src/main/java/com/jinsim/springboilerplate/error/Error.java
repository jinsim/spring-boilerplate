package com.jinsim.springboilerplate.error;

import lombok.Getter;

@Getter
public enum Error {
    INVALID_INPUT_VALUE("입력값이 올바르지 않습니다."),
    EMAIL_DUPLICATION("중복된 이메일입니다."),
    ACCOUNT_NOT_FOUND("계정을 찾을 수 없습니다."),
    INVALID_TOKEN("올바르지 않은 Token 입니다."),
    REFRESH_TOKEN_NOT_FOUND("로그아웃 된 사용자입니다.");

    private final String message;

    Error(String message) {
        this.message = message;
    }
}
