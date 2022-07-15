package com.musinsa.product.exception;

import com.musinsa.product.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    private ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        log.error("handleCustomException throw customException. message = {}", ex.getMessage());
        ErrorCode errorCode = ex.getErrorCode();

        return new ResponseEntity<>(new ErrorResponse(errorCode), errorCode.getHttpStatus());
    }

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
        String message = ex.getFieldError().getDefaultMessage();
        log.error("handleBindExceptionException throw bindException. message = {}", message);
        ErrorCode errorCode = ErrorCode.BAD_REQUEST;

        return new ResponseEntity<>(new ErrorResponse(errorCode, message), errorCode.getHttpStatus());
    }
}
