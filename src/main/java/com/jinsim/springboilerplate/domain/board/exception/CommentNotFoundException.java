package com.jinsim.springboilerplate.domain.board.exception;

import lombok.Getter;

import javax.persistence.EntityNotFoundException;

@Getter
public class CommentNotFoundException extends EntityNotFoundException {
    private String field;
    private Object value;

    public CommentNotFoundException(String field, Object value) {
        this.field = field;
        this.value = value;
    }
}
