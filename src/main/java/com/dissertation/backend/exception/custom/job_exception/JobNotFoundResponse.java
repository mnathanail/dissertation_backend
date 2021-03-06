package com.dissertation.backend.exception.custom.job_exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobNotFoundResponse {
    private int status;
    private String message;
    private long timeStamp;
}
