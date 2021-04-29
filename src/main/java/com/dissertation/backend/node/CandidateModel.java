package com.dissertation.backend.node;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidateModel {

    private Integer entityId;
    //private Integer working_years;
    private String name;
    private String email;
}
