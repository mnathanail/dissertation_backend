package com.dissertation.backend.node;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/*
@RelationshipEntity(type = "REQUIRES")
*/
@Getter
@Setter
@RelationshipProperties
public class JobRequiresRelationship {

    @TargetNode
    private SkillNode skillNode;

    @Property(name = "rel_uuid")
    private String relUuid;

    @Property(name = "years_of_experience")
    private Long yearsOfExperience;

    public JobRequiresRelationship(SkillNode skillNode, Long yearsOfExperience, String relUuid) {
        this.skillNode = skillNode;
        this.yearsOfExperience = yearsOfExperience;
        this.relUuid = relUuid;
    }

}
