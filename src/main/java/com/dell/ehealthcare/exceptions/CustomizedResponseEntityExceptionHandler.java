package com.dell.ehealthcare.exceptions;

import com.dell.ehealthcare.model.ExceptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
@RestController
@AllArgsConstructor
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ZonedDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotfoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotfoundException ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ZonedDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
