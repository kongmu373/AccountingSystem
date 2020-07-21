package com.kongmu373.accounting.exception;

import lombok.Data;

/**
 * Accounting System Exception.
 */
@Data
public class ServiceException extends RuntimeException {
    private int statusCode;
    private BizErrorCode errorCode; // biz error code
    private ErrorType errorType; // Service, Client, Unknown;

    public enum ErrorType {
        Client,
        Service,
        Unknown;
    }

    public ServiceException(String message) {
        super(message);
    }
}
