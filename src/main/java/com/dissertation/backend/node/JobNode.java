package com.dissertation.backend.node;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Node
@Getter
@Setter
@AllArgsConstructor
public class JobNode implements Serializable {

    @Id
    @GeneratedValue
    @JsonSerialize
    private Long id;

    @Property(name="job_id")
    private String jobId;

    @NotEmpty(message = "Job title is required")
    @Property(name="job_title")
    private String jobTitle;

    @NotEmpty(message = "Description is required")
    @Property(name="description")
    private String description;

    @Relationship(type = "REQUIRES", direction = Relationship.Direction.OUTGOING)
    private Set<JobRequiresRelationship> requiredSkills;

}
