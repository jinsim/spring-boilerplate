package com.jinsim.springboilerplate.error;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {

    private String message;
    private int status;
    private List<FieldError> detail = new ArrayList<>();


    @Builder
    public ErrorResponse(String message, int status, List<FieldError> detail) {
        this.message = message;
        this.status = status;
        this.detail = initDetail(detail);
    }

    @Builder
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    private List<FieldError> initDetail(List<FieldError> detail) {
        System.out.println("detail = " + detail);
        return (detail == null) ? new ArrayList<>() : detail;
    }

    @Getter
    public static class FieldError {
        private String field;
        private String message;
        private String value;

        @Builder
        public FieldError(String field, String message, String value) {
            this.field = field;
            this.message = message;
            this.value = value;
        }
    }
}