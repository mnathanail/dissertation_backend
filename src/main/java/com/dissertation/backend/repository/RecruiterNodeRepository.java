package com.dissertation.backend.repository;

import com.dissertation.backend.node.RecruiterNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "recruiter_node", collectionResourceRel = "recruiter_node")
public interface RecruiterNodeRepository extends Neo4jRepository<RecruiterNode, Long> {

    Optional<RecruiterNode> findByRecruiterId(String recruiterId);

    Optional<RecruiterNode> findByEntityId(Long recruiterId);

    @Query(
            "MATCH(r:RecruiterNode)-[:MANAGES]->(j:JobNode) WHERE j.job_id=$jobId RETURN r; "
    )
    RecruiterNode findRecruiterByJobId(String jobId);

    @Query("MATCH (c:RecruiterNode {entity_id: $recruiter_id}), " +
            "(e:ExperienceNode {experience_id: $exp_id})" +
            "MERGE (c)-[r:EXPERIENCE]->(e);")
    void createRelationRecruiterExperience(Long recruiter_id, String exp_id);

    @Query("MATCH (c:RecruiterNode {entity_id: $candidate_id}), " +
            "(e:EducationNode {education_id: $edu_id})" +
            "MERGE (c)-[r:EDUCATION]->(e);")
    void createRelationRecruiterEducation(Long candidate_id, String edu_id);

    @Query("MATCH (c:RecruiterNode {entity_id:$entityId})-[r:APPLIED_FOR]->(:JobNode) WHERE r.relUuid = $uuid DELETE r;")
    void deleteByRelUuidIn(Long entityId, String uuid);

    @Query("MATCH (:RecruiterNode)-[r:APPLIED_FOR]->(:JobNode) WHERE r.relUuid = $uuid RETURN r;")
    Optional<Boolean> checkIfRelationshipExists(String uuid);
}
