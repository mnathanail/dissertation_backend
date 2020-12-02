package com.dissertation.backend.repository;

import com.dissertation.backend.node.RecruiterNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "recruiter_node", collectionResourceRel = "recruiter_node")
public interface RecruiterNodeRepository extends Neo4jRepository<RecruiterNode, Long> {

}
