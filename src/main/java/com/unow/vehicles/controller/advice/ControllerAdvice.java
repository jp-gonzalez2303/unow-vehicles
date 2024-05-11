package com.unow.vehicles.controller.advice;

import com.unow.vehicles.config.exceptions.DataIntegrityViolationException;
import com.unow.vehicles.config.exceptions.Error;
import com.unow.vehicles.config.exceptions.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Error> handle(DataIntegrityViolationException e) {
        Error error = new Error();
        error.setCode(HttpStatus.CONFLICT.value());
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handle(ResourceNotFoundException e) {
        Error error = new Error();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<Error> handle(PropertyReferenceException e) {
        Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Error> handle(MissingServletRequestParameterException e) {
        Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handle(MethodArgumentNotValidException e) {

        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        Error error = new Error();
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(errorMessage);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


}
