package com.jinsim.springboilerplate.domain.account.exception;

import lombok.Getter;

import javax.persistence.EntityNotFoundException;

@Getter
public class AccountNotFoundException extends EntityNotFoundException {
    private String field;
    private Object value;

    public AccountNotFoundException(String field, Object value) {
        this.field = field;
        this.value = value;
    }
}
