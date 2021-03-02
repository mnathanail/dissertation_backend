package com.dissertation.backend.exception;

import com.dissertation.backend.exception.custom.candidate_exception.CandidateNotFoundException;
import com.dissertation.backend.exception.custom.candidate_exception.CandidateNotFoundResponse;
import com.dissertation.backend.exception.custom.education_exception.EducationNotFoundException;
import com.dissertation.backend.exception.custom.education_exception.EducationNotFoundResponse;
import com.dissertation.backend.exception.custom.experience_exception.ExperienceNotFoundException;
import com.dissertation.backend.exception.custom.experience_exception.ExperienceNotFoundResponse;
import com.dissertation.backend.exception.custom.job_exception.JobNotFoundException;
import com.dissertation.backend.exception.custom.job_exception.JobNotFoundResponse;
import com.dissertation.backend.exception.custom.recruiter_exception.RecruiterNotFoundException;
import com.dissertation.backend.exception.custom.recruiter_exception.RecruiterNotFoundResponse;
import com.dissertation.backend.response.DataIntegrityViolationResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@RestControllerAdvice
public class RestAuthenticationEntryPoint extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ClassNotFoundException.class} )
    public ResponseEntity<Object> handleExpiredJwtException(ClassNotFoundException ex, WebRequest request) {
        System.out.println("ExpiredJwtException");
        return new ResponseEntity<Object>(
                "Jwt has expired "+ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

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

    @ExceptionHandler({EducationNotFoundException.class})
    protected ResponseEntity<EducationNotFoundResponse> handleException(EducationNotFoundException e, Locale locale) {
        EducationNotFoundResponse enfr = new EducationNotFoundResponse();
        enfr.setStatus(HttpStatus.NOT_FOUND.value());
        enfr.setMessage(e.getMessage());
        enfr.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(enfr, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    protected ResponseEntity<EducationNotFoundResponse> handleException(Exception e, Locale locale) {
        EducationNotFoundResponse enfr = new EducationNotFoundResponse();
        enfr.setStatus(HttpStatus.BAD_REQUEST.value());
        enfr.setMessage(e.getMessage());
        enfr.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(enfr, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ExperienceNotFoundException.class})
    protected ResponseEntity<ExperienceNotFoundResponse> handleException(ExperienceNotFoundException e, Locale locale) {
        ExperienceNotFoundResponse enfr = new ExperienceNotFoundResponse();
        enfr.setStatus(HttpStatus.BAD_REQUEST.value());
        enfr.setMessage(e.getMessage());
        enfr.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(enfr, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CandidateNotFoundException.class})
    protected ResponseEntity<CandidateNotFoundResponse> handleException(CandidateNotFoundException e, Locale locale) {
        CandidateNotFoundResponse enfr = new CandidateNotFoundResponse();
        enfr.setStatus(HttpStatus.NOT_FOUND.value());
        enfr.setMessage(e.getMessage());
        enfr.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(enfr, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RecruiterNotFoundException.class})
    protected ResponseEntity<RecruiterNotFoundResponse> handleException(RecruiterNotFoundException e, Locale locale) {
        RecruiterNotFoundResponse rnfr = new RecruiterNotFoundResponse();
        rnfr.setStatus(HttpStatus.NOT_FOUND.value());
        rnfr.setMessage(e.getMessage());
        rnfr.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(rnfr, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({JobNotFoundException.class})
    protected ResponseEntity<JobNotFoundResponse> handleException(JobNotFoundException e, Locale locale) {
        JobNotFoundResponse enfr = new JobNotFoundResponse();
        enfr.setStatus(HttpStatus.NOT_FOUND.value());
        enfr.setMessage(e.getMessage());
        enfr.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(enfr, HttpStatus.NOT_FOUND);
    }
}
