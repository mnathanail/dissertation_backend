package com.dissertation.backend.node;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Node
@Data
public class RecruiterNode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonSerialize
    private Long id;

    @Property(name = "name")
    private String name;

    @Relationship(type = "MANAGES", direction = Relationship.Direction.OUTGOING )
    private List<JobNode> jobNodeAdvertisements = new ArrayList<>();

}
