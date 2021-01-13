package com.dissertation.backend.node;

import com.dissertation.backend.node.converters.PeriodConverter;
import com.dissertation.backend.node.shared.Period;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import javax.validation.constraints.NotEmpty;

@Node
@Getter
@Setter
public class EducationNode {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "education_id")
    public String educationId;

    @NotEmpty(message = "Title is required")
    @Property(name="title")
    private String title;

    @NotEmpty(message = "Degree is required")
    @Property(name="degree")
    private String degree;

    @NotEmpty(message = "School is required")
    @Property(name="school")
    private String school;

    @CompositeProperty(converter = PeriodConverter.class)
    private Period period;

}
