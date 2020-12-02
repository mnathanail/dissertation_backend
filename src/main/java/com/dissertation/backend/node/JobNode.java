package com.dissertation.backend.node;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.io.Serializable;

@Node
@Getter
@Setter
@AllArgsConstructor
public class JobNode implements Serializable {

    @Id
    @GeneratedValue
    @JsonSerialize
    private Long id;

    @Property(name="title")
    private String title;

    @Property(name="description")
    private String description;

/*
    @Relationship(type = "REQUIRES", direction = Relationship.Direction.OUTGOING)
    private List<Skill> requiredSkills = new ArrayList<>();
*/

}
