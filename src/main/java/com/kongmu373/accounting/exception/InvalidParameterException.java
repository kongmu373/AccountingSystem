package com.kongmu373.accounting.exception;

import org.springframework.http.HttpStatus;

public class InvalidParameterException extends ServiceException {
    public InvalidParameterException(String message) {
        super(message);
        this.setStatusCode(HttpStatus.BAD_REQUEST.value());
        this.setErrorType(ErrorType.Client);
        this.setErrorCode("INVALID_PARAMETER");
    }
}
