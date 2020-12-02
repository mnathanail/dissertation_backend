package com.dissertation.backend.node;

/*
@RelationshipEntity(type = "HAS_EDUCATION")
*/
public class CandidateEducationRelationship {

    private Long id;

/*
    @StartNode
*/
    CandidateNode candidateNode;
/*
    @EndNode
*/
    EducationNode educationNode;

    public CandidateEducationRelationship(CandidateNode candidateNode, EducationNode educationNode) {
        this.candidateNode = candidateNode;
        this.educationNode = educationNode;

/*
        this.candidateNode.getEducationNodes().add(this.educationNode);
*/
    }
}
