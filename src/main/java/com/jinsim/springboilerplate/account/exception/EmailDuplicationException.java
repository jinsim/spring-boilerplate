package com.jinsim.springboilerplate.account.exception;

import lombok.Getter;

@Getter
public class EmailDuplicationException extends RuntimeException{
    private String field = "email";
    private String value;

    public EmailDuplicationException(String email) {
        this.value = email;
    }
}
