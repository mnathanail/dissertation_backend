package com.dissertation.backend.exception.custom.recruiter_exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecruiterNotFoundResponse {
    private int status;
    private String message;
    private long timeStamp;
}
