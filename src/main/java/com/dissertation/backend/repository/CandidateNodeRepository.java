package com.dissertation.backend.repository;

import com.dissertation.backend.node.CandidateNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

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
}
