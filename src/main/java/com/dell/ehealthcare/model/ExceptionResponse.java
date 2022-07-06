package com.dell.ehealthcare.model;

import java.time.ZonedDateTime;

public class ExceptionResponse {

    private ZonedDateTime ts;

    private String message;

    private String description;

    public ExceptionResponse(ZonedDateTime ts, String message, String description){
        super();
        this.ts = ts;
        this.message = message;
        this.description = description;
    }
}
