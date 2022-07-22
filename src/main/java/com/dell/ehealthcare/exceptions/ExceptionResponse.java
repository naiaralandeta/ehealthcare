package com.dell.ehealthcare.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZonedDateTime;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
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