package com.kongmu373.accounting.exception;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // DRY: Don't repeat yourself
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
}
