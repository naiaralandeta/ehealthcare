package com.dell.ehealthcare.exceptions;

public class MedicineNotfoundException extends RuntimeException {

    public MedicineNotfoundException() {
    }

    public MedicineNotfoundException(String message) {
        super(message);
    }

    public MedicineNotfoundException(Throwable cause) {
        super(cause);
    }

    public MedicineNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MedicineNotfoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
