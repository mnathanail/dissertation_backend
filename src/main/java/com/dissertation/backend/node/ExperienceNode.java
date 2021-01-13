package com.dissertation.backend.node;

import com.dissertation.backend.node.converters.PeriodConverter;
import com.dissertation.backend.node.shared.Period;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import javax.validation.constraints.NotEmpty;

@Node
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceNode {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "experience_id")
    public String experienceId;

    @NotEmpty(message = "JobTitle is required")
    @Property(name = "job_title")
    private String jobTitle;

    @NotEmpty(message = "Company name is required")
    @Property(name = "company_name")
    private String companyName;

    @Property(name = "industry")
    private String industry;

    @Property(name = "description")
    private String description;

    @Property(name = "is_current")
    private Boolean isCurrent;

    @CompositeProperty(converter = PeriodConverter.class)
    private Period period;

}

