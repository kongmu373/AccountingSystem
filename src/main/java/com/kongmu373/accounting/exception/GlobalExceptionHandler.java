package com.kongmu373.accounting.exception;

import lombok.val;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    ResponseEntity<?> handleServiceException(ServiceException ex) {
        val errorResponse = ErrorResponse.builder()
                                .statusCode(ex.getStatusCode())
                                .message(ex.getMessage())
                                .errorType(ex.getErrorType())
                                .code(ex.getErrorCode())
                                .build();
        return ResponseEntity.status(errorResponse.getStatusCode() != 0 ? errorResponse.getStatusCode()
                                         : HttpStatus.INTERNAL_SERVER_ERROR.value())
                   .body(errorResponse);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    ResponseEntity<?> handleIncorrectCredentialsException(IncorrectCredentialsException ex) {
        val errorResponse = ErrorResponse.builder()
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .errorType(ServiceException.ErrorType.Client)
                                .code(BizErrorCode.INVALID_PARAMETER)
                                .message(ex.getMessage())
                                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                   .body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        val errorResponse = ErrorResponse.builder()
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .errorType(ServiceException.ErrorType.Client)
                                .code(BizErrorCode.INVALID_PARAMETER)
                                .message(ex.getMessage())
                                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                   .body(errorResponse);
    }

    @ExceptionHandler(NumberFormatException.class)
    ResponseEntity<?> handleNumberFormatException(NumberFormatException ex) {
        val errorResponse = ErrorResponse.builder()
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .errorType(ServiceException.ErrorType.Client)
                                .code(BizErrorCode.INVALID_PARAMETER)
                                .message(ex.getMessage())
                                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                   .body(errorResponse);
    }
}
