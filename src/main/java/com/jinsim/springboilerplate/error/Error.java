package com.jinsim.springboilerplate.error;

import lombok.Getter;

@Getter
public enum Error {
    INVALID_INPUT_VALUE("입력값이 올바르지 않습니다.", 400);

    private final String message;
    private final int status;

    Error(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
