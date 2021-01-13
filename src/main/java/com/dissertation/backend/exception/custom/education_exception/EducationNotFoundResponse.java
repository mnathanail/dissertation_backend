package com.dissertation.backend.exception.custom.education_exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EducationNotFoundResponse {
    private int status;
    private String message;
    private long timeStamp;
}
