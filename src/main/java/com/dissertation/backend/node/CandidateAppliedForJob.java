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
public class CandidateAppliedForJob {


    @TargetNode
    private JobNode jobNode;

    @Property(name = "relUuid")
    private String relUuid;

    public CandidateAppliedForJob(JobNode jobNode, String relUuid){
        this.jobNode = jobNode;
        this.relUuid = relUuid;
    }
}
