package com.dell.ehealthcare.exceptions;

public class RoleNotfoundException extends RuntimeException {

    public RoleNotfoundException() {
    }

    public RoleNotfoundException(String message) {
        super(message);
    }

    public RoleNotfoundException(Throwable cause) {
        super(cause);
    }

    public RoleNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotfoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
