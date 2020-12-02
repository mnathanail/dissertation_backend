package com.dissertation.backend.node;


import lombok.Getter;
import lombok.Setter;

/*
@RelationshipEntity(type = "REQUIRES")
*/
@Getter
@Setter
public class JobRequiresRelationship {

 /*   private Integer id;

//    @StartNode
    private JobNode jobNode;

//    @EndNode
    private Skill skill;

    public JobRequiresRelationship(JobNode jobNode, Skill skill){
        this.jobNode = jobNode;
        //this.skill = skill;
        this.jobNode.getRequiredSkills().add(this.skill);
        //this.skill.getJobNodes().add(this.jobNode);
    }*/
}
