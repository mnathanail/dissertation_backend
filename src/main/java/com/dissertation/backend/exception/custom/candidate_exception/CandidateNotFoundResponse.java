package com.dissertation.backend.exception.custom.candidate_exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CandidateNotFoundResponse {
    private int status;
    private String message;
    private long timeStamp;
}
