package com.jinsim.springboilerplate.domain.board.exception;

import lombok.Getter;

@Getter
public class PostLikeException extends RuntimeException {
    private String field = "method";
    private String value;


    public PostLikeException(String message, String value) {
        super(message);
        this.value = value;
    }
}