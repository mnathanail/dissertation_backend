package com.dissertation.backend.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Node
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecruiterNode implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @Property(name = "entity_id")
    private Long entityId;

    @Property(name = "recruiter_id")
    private String recruiterId;

    @NotEmpty(message = "Name is required")
    @Property(name = "name")
    private String name;

    @NotEmpty(message = "Email is required")
    @Property(name = "email")
    private String email;

    @Relationship(type = "MANAGES", direction = Relationship.Direction.OUTGOING )
    private Set<JobNode> jobNodeAdvertisements = new HashSet<>();

    @Relationship(type = "EDUCATION", direction = Relationship.Direction.OUTGOING)
    private Set<EducationNode> educationNodes;

}
