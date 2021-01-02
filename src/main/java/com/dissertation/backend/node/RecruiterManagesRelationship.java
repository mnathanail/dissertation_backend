package com.dissertation.backend.node;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;


@Getter
@Setter
@RelationshipProperties
public class RecruiterManagesRelationship {

    @TargetNode
    private JobNode jobNode;

    public RecruiterManagesRelationship(JobNode jobNode) {
        this.jobNode = jobNode;
    }

}
