package com.dissertation.backend.node;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendationExtendedModel {
    private String candidateName;
    private Long candidateEntityId;
    private String[] haveSkillNames;
    private String[] totalSkillNames;
    private double percent;
    private int totalSkillsNumber;
    private int candidateSkillNumber;

}

