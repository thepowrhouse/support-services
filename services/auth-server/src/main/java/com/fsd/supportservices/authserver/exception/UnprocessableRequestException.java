package com.fsd.supportservices.authserver.exception;

public class UnprocessableRequestException extends RuntimeException {

    public UnprocessableRequestException() {
    }

    public UnprocessableRequestException(String message) {
        super(message);
    }

    public UnprocessableRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnprocessableRequestException(Throwable cause) {
        super(cause);
    }

    public UnprocessableRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}