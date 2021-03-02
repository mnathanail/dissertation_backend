package com.dissertation.backend.exception.custom.experience_exception;


public class ExperienceNotFoundException extends RuntimeException{

    public ExperienceNotFoundException(String message) {
        super(message);
    }

    public ExperienceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExperienceNotFoundException(Throwable cause) {
        super(cause);
    }
}
