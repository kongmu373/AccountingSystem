package com.kongmu373.accounting.exception;

import static com.kongmu373.accounting.exception.BizErrorCode.INVALID_PARAMETER;

import org.springframework.http.HttpStatus;

public class InvalidParameterException extends ServiceException {
    /**
     * Constructor for InvalidParameterException.
     * @param message thrown message.
     */
    public InvalidParameterException(String message) {
        super(message);
        this.setStatusCode(HttpStatus.BAD_REQUEST.value());
        this.setErrorType(ErrorType.Client);
        this.setErrorCode(INVALID_PARAMETER);
    }
}
