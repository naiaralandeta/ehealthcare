package com.dell.ehealthcare.exceptions;

public class OrderNotfoundException extends RuntimeException {

    public OrderNotfoundException() {
    }

    public OrderNotfoundException(String message) {
        super(message);
    }

    public OrderNotfoundException(Throwable cause) {
        super(cause);
    }

    public OrderNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotfoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
