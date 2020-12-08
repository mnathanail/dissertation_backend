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

/*
    @Relationship(type = "APPLIED_FOR", direction = Relationship.Direction.OUTGOING)
    private List<JobNode> jobNodes;

    @Relationship(type = "EDUCATION", direction = Relationship.Direction.OUTGOING)
    private List<EducationNode> educationNodes;*/

    @Relationship(type = "KNOWS", direction = Relationship.Direction.OUTGOING)
    private Set<CandidateSkillRelationship> candidateSkillRelationships = new HashSet<>();

    @Relationship(type = "EXPERIENCE", direction = Relationship.Direction.OUTGOING)
    private Set<ExperienceNode> experienceNodes = new HashSet<>();

/*    @Relationship(type = "KNOWS", direction = Relationship.Direction.OUTGOING)
    private Set<SkillNode> candidateAndSkills = new HashSet<>();*/

}
