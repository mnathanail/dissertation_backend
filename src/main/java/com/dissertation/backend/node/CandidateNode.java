package com.dissertation.backend.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Name is required")
    @Property(name="name")
    private String name;

    @Email
    @NotEmpty(message = "Email is required")
    @Property(name="email")
    private String email;

    @Property(name = "candidate_id")
    private String candidateId;

    @Property(name="working_years")
    private Long workingYears;

    @Relationship(type = "EDUCATION", direction = Relationship.Direction.OUTGOING)
    private Set<EducationNode> educationNodes;

    @Relationship(type = "EXPERIENCE", direction = Relationship.Direction.OUTGOING)
    private Set<ExperienceNode> experienceNodes = new HashSet<>();

    @Relationship(type = "KNOWS", direction = Relationship.Direction.OUTGOING)
    private Set<CandidateSkillRelationship> candidateSkillRelationships = new HashSet<>();

    @Relationship(type = "APPLIED_FOR", direction = Relationship.Direction.OUTGOING)
    private Set<CandidateAppliedForJob> jobNodes;

}
