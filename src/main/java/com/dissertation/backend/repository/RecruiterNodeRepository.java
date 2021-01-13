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
}
