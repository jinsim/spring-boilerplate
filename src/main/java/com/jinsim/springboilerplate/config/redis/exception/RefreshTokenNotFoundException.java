package com.jinsim.springboilerplate.config.redis.exception;

public class RefreshTokenNotFoundException extends RuntimeException{
    private String field = "redisKey";
    private Object value;

    public RefreshTokenNotFoundException(Object value) {
        this.value = value;
    }
}
