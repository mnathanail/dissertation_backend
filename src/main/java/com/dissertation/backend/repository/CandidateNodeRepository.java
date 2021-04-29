package com.dissertation.backend.repository;

import com.dissertation.backend.model.SkillEntityModel;
import com.dissertation.backend.node.CandidateNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "candidateNode", path = "candidate_node")
public interface CandidateNodeRepository extends Neo4jRepository<CandidateNode, Long> {

    Optional<CandidateNode> findCandidateNodeByEntityId(Long entity_id);

    @Query("MATCH (c:CandidateNode {entity_id: $candidate_id}), " +
            "(e:ExperienceNode {experience_id: $exp_id})" +
            "MERGE (c)-[r:EXPERIENCE]->(e);")
    void createRelationCandidateExperience(Long candidate_id, String exp_id);

    @Query("MATCH (c:CandidateNode {entity_id: $candidate_id}), " +
            "(e:EducationNode {education_id: $edu_id})" +
            "MERGE (c)-[r:EDUCATION]->(e);")
    void createRelationCandidateEducation(Long candidate_id, String edu_id);

    @Query("MATCH (c:CandidateNode {entity_id:$entityId})-[r:APPLIED_FOR]->(:JobNode) WHERE r.relUuid = $uuid DELETE r;")
    void deleteByRelUuidIn(Long entityId, String uuid);

    @Query("MATCH (:CandidateNode)-[r:APPLIED_FOR]->(:JobNode) WHERE r.relUuid = $uuid RETURN r;")
    Optional<Boolean> checkIfRelationshipExists(String uuid);

    @Query(
            " UNWIND $rows AS row " +
            " MATCH  (s:SkillNode) " +
            " WHERE s.entity_id = row.entity_id " +
            " WITH  collect(row) as rows, collect(s) as allSkills " +
            " UNWIND rows as row " +
            " MATCH (c:CandidateNode)-[r:KNOWS]->(s:SkillNode) " +
            " WHERE r.years_of_experience >= row.yearsOfExperience AND s in allSkills " +
            " WITH c, collect(distinct r) as rels, collect(distinct s) as skills, allSkills " +
            " WHERE ALL(sk in allSkills where sk in skills) " +
            " RETURN c;" //, rels, skills
    )
    Set<CandidateNode> searchCandidatesBySkillKeywords(List<SkillEntityModel> rows);
}
