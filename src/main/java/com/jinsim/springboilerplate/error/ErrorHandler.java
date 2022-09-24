package com.jinsim.springboilerplate.error;

import com.jinsim.springboilerplate.account.exception.EmailDuplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
        return buildFieldErrors(Error.INVALID_INPUT_VALUE, fieldErrors);
    }

    @ExceptionHandler(EmailDuplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleConstraintViolationException(EmailDuplicationException e) {
        final Error error = Error.EMAIL_DUPLICATION;
        log.error(error.getMessage(), e.getEmail() + e.getField());
        return buildError(error);
    }

    private ErrorResponse buildError(Error error) {
        ErrorResponse retError = ErrorResponse.builder()
                .status(error.getStatus())
                .message(error.getMessage())
                .build();
        return retError;
    }

    private List<ErrorResponse.FieldError> getFieldErrors(BindingResult bindingResult) {
        final List<FieldError> detail = bindingResult.getFieldErrors();
        return detail.parallelStream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .message(error.getDefaultMessage())
                        .field(error.getField())
                        .value((String) error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
    }

    private ErrorResponse buildFieldErrors(Error error, List<ErrorResponse.FieldError> detail) {
        return ErrorResponse.builder()
                .status(error.getStatus())
                .message(error.getMessage())
                .detail(detail)
                .build();
    }
}
