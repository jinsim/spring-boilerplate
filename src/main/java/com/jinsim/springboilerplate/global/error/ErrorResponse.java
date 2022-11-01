package com.jinsim.springboilerplate.global.error;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {

    private String message;
    private List<FieldError> detail = new ArrayList<>();

    @Builder
    public ErrorResponse(String message, List<FieldError> detail) {
        this.message = message;
        this.detail = initDetail(detail);
    }

    @Builder
    public ErrorResponse(String message) {
        this.message = message;
    }

    private List<FieldError> initDetail(List<FieldError> detail) {
        return (detail == null) ? new ArrayList<>() : detail;
    }

    @Getter
    public static class FieldError {
        private String field;
        // 여러 타입을 받을 수 있도록 Object로 설정
        private Object value;
        private String message;

        @Builder
        public FieldError(String field, Object value, String message) {
            this.field = field;
            this.value = value;
            this.message = message;
        }
    }
}