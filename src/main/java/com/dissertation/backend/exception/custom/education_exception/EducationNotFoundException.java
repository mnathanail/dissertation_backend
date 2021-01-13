package com.dissertation.backend.exception.custom.education_exception;


public class EducationNotFoundException extends RuntimeException{

    public EducationNotFoundException(String message) {
        super(message);
    }

    public EducationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EducationNotFoundException(Throwable cause) {
        super(cause);
    }
}
