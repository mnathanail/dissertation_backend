package com.dissertation.backend.exception.custom.recruiter_exception;


public class RecruiterNotFoundException extends RuntimeException{

    public RecruiterNotFoundException(String message) {
        super(message);
    }

    public RecruiterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecruiterNotFoundException(Throwable cause) {
        super(cause);
    }
}
