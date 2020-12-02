package com.dissertation.backend.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.io.Serializable;
import java.util.List;

@Node
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateNode implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @Property(name = "entity_id")
    private Long entityId;

    @Property(name="name")
    private String name;

    @Property(name="email")
    private String email;

    @Property(name="working_years")
    private Long working_years;

/*    @Relationship(type = "KNOWS")
    private List<Skill> skills;

    @Relationship(type = "APPLIED_FOR")
    private List<JobNode> jobNodes;

    @Relationship(type = "EDUCATION")
    private List<EducationNode> educationNodes;*/

    @Relationship(type = "EXPERIENCE", direction = Relationship.Direction.OUTGOING)
    private List<ExperienceNode> experienceNodes;

/*
    @Relationship(type = "KNOWS", direction = Relationship.Direction.OUTGOING)
    private List<Skill> candidateAndSkills;
*/

}
