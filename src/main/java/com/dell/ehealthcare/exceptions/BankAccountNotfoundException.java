package com.dell.ehealthcare.exceptions;

public class BankAccountNotfoundException extends RuntimeException {

    public BankAccountNotfoundException() {
    }

    public BankAccountNotfoundException(String message) {
        super(message);
    }

    public BankAccountNotfoundException(Throwable cause) {
        super(cause);
    }

    public BankAccountNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankAccountNotfoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}