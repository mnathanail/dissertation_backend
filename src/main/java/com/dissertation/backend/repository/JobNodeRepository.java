package com.dissertation.backend.repository;

import com.dissertation.backend.node.JobNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "job", path = "job")
public interface JobNodeRepository extends Neo4jRepository<JobNode, Long> {

}
