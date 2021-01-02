package com.dissertation.backend.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

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

    @Property(name = "name")
    private String name;

    @Relationship(type = "MANAGES", direction = Relationship.Direction.OUTGOING )
    private Set<JobNode> jobNodeAdvertisements = new HashSet<>();

}
