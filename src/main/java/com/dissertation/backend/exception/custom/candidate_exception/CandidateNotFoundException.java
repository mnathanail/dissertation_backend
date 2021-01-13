package com.dissertation.backend.exception.custom.candidate_exception;


public class CandidateNotFoundException extends RuntimeException{

    public CandidateNotFoundException(String message) {
        super(message);
    }

    public CandidateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CandidateNotFoundException(Throwable cause) {
        super(cause);
    }
}
