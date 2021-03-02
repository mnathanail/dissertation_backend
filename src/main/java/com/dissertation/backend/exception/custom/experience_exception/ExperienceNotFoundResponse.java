package com.dissertation.backend.exception.custom.experience_exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExperienceNotFoundResponse {
    private int status;
    private String message;
    private long timeStamp;
}
