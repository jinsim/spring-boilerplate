package com.jinsim.springboilerplate.error;

import com.jinsim.springboilerplate.account.exception.AccountNotFoundException;
import com.jinsim.springboilerplate.account.exception.EmailDuplicationException;
import com.jinsim.springboilerplate.config.jwt.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final Error error = Error.INVALID_INPUT_VALUE;
        final List<ErrorResponse.FieldError> fieldErrors = getFieldErrorsByBindingResult(e.getBindingResult());
        return buildFieldErrors(error, fieldErrors);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleAccountNotFoundException(AccountNotFoundException e) {
        final Error error = Error.ACCOUNT_NOT_FOUND;
        log.error("{} {} : {}", error.getMessage(), e.getField(), e.getValue());
        List<ErrorResponse.FieldError> fieldError = getFieldError(e.getField(), e.getValue());
        return buildFieldErrors(error, fieldError);
    }

    @ExceptionHandler(EmailDuplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleConstraintViolationException(EmailDuplicationException e) {
        final Error error = Error.EMAIL_DUPLICATION;
        log.error("{} {} : {}", error.getMessage(), e.getField(), e.getValue());
        List<ErrorResponse.FieldError> fieldError = getFieldError(e.getField(), e.getValue());
        return buildFieldErrors(error, fieldError);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleInvalidTokenException(InvalidTokenException e) {
        final Error error = Error.INVALID_TOKEN;
        log.error("{} {} : {}", error.getMessage(), e.getField(), e.getValue());
        List<ErrorResponse.FieldError> fieldError = getFieldError(e.getField(), e.getValue(), e.getMessage());
        return buildFieldErrors(error, fieldError);
    }


    private ErrorResponse buildError(Error error) {
        ErrorResponse retError = ErrorResponse.builder()
                .message(error.getMessage())
                .build();
        return retError;
    }

    private List<ErrorResponse.FieldError> getFieldErrorsByBindingResult(BindingResult bindingResult) {
        final List<FieldError> detail = bindingResult.getFieldErrors();
        return detail.parallelStream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .message(error.getDefaultMessage())
                        .field(error.getField())
                        .value(error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
    }

    private List<ErrorResponse.FieldError> getFieldError(String field, Object value) {
        List<ErrorResponse.FieldError> fieldError =  new ArrayList<>(Arrays.asList(ErrorResponse.FieldError.builder()
                .field(field)
                .value(value)
                .build()));
        return fieldError;
    }

    private List<ErrorResponse.FieldError> getFieldError(String field, Object value, String message) {
        List<ErrorResponse.FieldError> fieldError =  new ArrayList<>(Arrays.asList(ErrorResponse.FieldError.builder()
                .field(field)
                .value(value)
                .message(message)
                .build()));
        return fieldError;
    }


    private ErrorResponse buildFieldErrors(Error error, List<ErrorResponse.FieldError> detail) {
        return ErrorResponse.builder()
                .message(error.getMessage())
                .detail(detail)
                .build();
    }
}
