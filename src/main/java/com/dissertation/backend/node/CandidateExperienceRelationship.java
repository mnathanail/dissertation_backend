package com.dissertation.backend.node;

/*
@RelationshipEntity(value = "EXPERIENCE")
*/
public class CandidateExperienceRelationship {

    private Long id;
/*
    @StartNode
*/
    CandidateNode candidateNode;

    ExperienceNode experienceNode;

    public CandidateExperienceRelationship(CandidateNode candidateNode, ExperienceNode experienceNode) {
        this.candidateNode = candidateNode;
        this.experienceNode = experienceNode;

        this.candidateNode.getExperienceNodes().add(this.experienceNode);

    }
}
