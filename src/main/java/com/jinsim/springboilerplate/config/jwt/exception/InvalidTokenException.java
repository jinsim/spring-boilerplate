package com.jinsim.springboilerplate.config.jwt.exception;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {
    private String field;
    private String value;
    private String message;

    public InvalidTokenException(String field, String value, String message) {
        this.field = field;
        this.value = value;
        this.message = message;
    }
}
