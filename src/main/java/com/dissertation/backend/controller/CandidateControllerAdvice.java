package com.dissertation.backend.controller;

import com.dissertation.backend.response.DataIntegrityViolationResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CandidateControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<?> handleException(Exception e) {
        DataIntegrityViolationResponse response = null;

        if(e instanceof DataIntegrityViolationException){
            DataIntegrityViolationException ex = (DataIntegrityViolationException) e;
            response = new DataIntegrityViolationResponse((long) 1062, "This mobile no is already Registered!");
            return new ResponseEntity<DataIntegrityViolationResponse>(response, HttpStatus.CONFLICT);
        }
        return null;
    }
}
