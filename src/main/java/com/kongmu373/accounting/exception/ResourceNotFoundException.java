package com.kongmu373.accounting.exception;

import org.springframework.http.HttpStatus;

/**
 * Accounting System ResourceNotFoundException.
 */
public class ResourceNotFoundException extends ServiceException {
    /**
     * Constructor for ResourceNotFoundException.
     * @param message thrown error message
     */
    public ResourceNotFoundException(String message) {
        super(message);
        this.setStatusCode(HttpStatus.NOT_FOUND.value());
        this.setErrorCode("NOT_FOUND");
        this.setErrorType(ErrorType.Client);
    }
}
