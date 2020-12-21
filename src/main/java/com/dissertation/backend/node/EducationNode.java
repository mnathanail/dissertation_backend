package com.dissertation.backend.node;

import com.dissertation.backend.node.converters.PeriodConverter;
import com.dissertation.backend.node.shared.Period;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

@Node
@Getter
@Setter
public class EducationNode {

    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty("educationId")
    @Property(name = "education_id")
    public String educationId;

    @JsonProperty("title")
    @Property(name="title")
    private String title;

    @JsonProperty("degree")
    @Property(name="degree")
    private String degree;

    @JsonProperty("school")
    @Property(name="school")
    private String school;

    @JsonProperty("period")
    @CompositeProperty(converter = PeriodConverter.class)
    private Period period;

}
