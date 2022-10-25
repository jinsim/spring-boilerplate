package com.jinsim.springboilerplate.global.redis.exception;

import lombok.Getter;

@Getter
public class RefreshTokenNotFoundException extends RuntimeException{
    private String field = "redisKey";
    private Object value;

    public RefreshTokenNotFoundException(Object value) {
        this.value = value;
    }
}
