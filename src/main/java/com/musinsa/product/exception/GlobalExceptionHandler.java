package com.musinsa.product.exception;

import com.musinsa.product.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    private ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        log.error("handleCustomException catch exception. message = {}", ex.getMessage());
        ErrorCode errorCode = ex.getErrorCode();

        return new ResponseEntity<>(new ErrorResponse(errorCode), errorCode.getHttpStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
        String message = ex.getFieldError().getDefaultMessage();
        log.error("handleBindException catch exception. message = {}", message);
        ErrorCode errorCode = ErrorCode.BAD_REQUEST;

        return new ResponseEntity<>(new ErrorResponse(errorCode, message), errorCode.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getMessage();
        log.error("handleConstraintViolationException catch exception. message = {}", message);
        ErrorCode errorCode = ErrorCode.BAD_REQUEST;

        return new ResponseEntity<>(new ErrorResponse(errorCode, message), errorCode.getHttpStatus());
    }
}
