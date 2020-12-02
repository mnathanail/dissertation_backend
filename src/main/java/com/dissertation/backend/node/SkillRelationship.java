package com.dissertation.backend.node;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@RelationshipProperties
public class SkillRelationship {

    /*@StartNode*/
    @TargetNode
    private CandidateNode candidateNode;

    /*@EndNode*/
    /*private Skill skill;*/

    @Property(name = "years_of_experience")
    private Long yoe;

    public SkillRelationship(CandidateNode candidateNode, Long yoe){
        this.candidateNode = candidateNode;
        this.yoe = yoe;

/*Ca
        this.candidateNode.getSkills().add( this.skill);
*/
        //this.skill.setYearsOfExperience(this.years_of_experience);
       // this.skill.getNodeCandidateNodes().add(this.candidateNode);
    }

}
