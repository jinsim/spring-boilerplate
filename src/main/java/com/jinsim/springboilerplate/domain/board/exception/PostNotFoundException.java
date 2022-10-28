package com.jinsim.springboilerplate.domain.board.exception;

import lombok.Getter;

import javax.persistence.EntityNotFoundException;

@Getter
public class PostNotFoundException extends EntityNotFoundException {
    private String field;
    private Object value;

    public PostNotFoundException(String field, Object value) {
        this.field = field;
        this.value = value;
    }
}
