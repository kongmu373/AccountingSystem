package com.kongmu373.accounting.exception;

import static com.kongmu373.accounting.exception.BizErrorCode.NO_AUTHORIZED;

import org.springframework.http.HttpStatus;

/**
 * Accounting System UnAuthorizedException.
 */
public class UnAuthorizedException extends ServiceException {
    /**
     * Constructor for UnAuthorizedException.
     *
     * @param message thrown error message
     */
    public UnAuthorizedException(String message) {
        super(message);
        this.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        this.setErrorCode(NO_AUTHORIZED);
        this.setErrorType(ErrorType.Client);
    }
}
