package com.dissertation.backend.node;

import com.dissertation.backend.node.converters.PeriodConverter;
import com.dissertation.backend.node.shared.Period;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

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

    @JsonProperty("experienceId")
    @Property(name = "experience_id")
    public String experienceId;

    @JsonProperty("jobTitle")
    @Property(name = "job_title")
    private String jobTitle;

    @JsonProperty("companyName")
    @Property(name = "company_name")
    private String companyName;

    @JsonProperty("industry")
    @Property(name = "industry")
    private String industry;

    @JsonProperty("description")
    @Property(name = "description")
    private String description;

    @JsonProperty("isCurrent")
    @Property(name = "is_current")
    private Boolean isCurrent;

    @JsonProperty("period")
    @CompositeProperty(converter = PeriodConverter.class)
    private Period period;

}

