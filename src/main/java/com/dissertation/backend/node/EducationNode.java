package com.dissertation.backend.node;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node
@Getter
@Setter
public class EducationNode {
    @Id
    @GeneratedValue
    @JsonSerialize
    private Long id;

    @Property(name="title")
    private String title;

    @Property(name="degree")
    private String degree;

    @Property(name="school")
    private String school;

/*    @Property(name="period")
    private List<Period> period;

    @Transient
    @Builder
    final static class Period{
        private final String start;
        private final String end;
    }*/

}
