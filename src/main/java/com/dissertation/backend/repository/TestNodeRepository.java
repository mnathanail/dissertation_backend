package com.dissertation.backend.repository;

import com.dissertation.backend.node.TestNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface TestNodeRepository extends Neo4jRepository<TestNode, Long> {

    @Query("CREATE (n:TestNode {name: 'manos'}) RETURN n")
    TestNode createTestNode(TestNode testNode);
}
