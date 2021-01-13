package com.dissertation.backend.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import javax.validation.constraints.NotEmpty;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Node
public class SkillNode {

    @Id
    @NotEmpty(message = "Name is required")
    private String name;

    @Property(name = "entity_id")
    private Long entityId;


}
