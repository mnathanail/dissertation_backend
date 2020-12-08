package com.dissertation.backend.repository;

import com.dissertation.backend.config.CustomSkillsConverter;
import com.dissertation.backend.node.TestNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(excerptProjection = CustomSkillsConverter.class)
public interface TestNodeRepository extends Neo4jRepository<TestNode, Long> {


   /* @Query("UNWIND :#{#testNode} as row CREATE (n:TestNode {name: row.name}) RETURN n")
    TestNode createTestNode(List<TestNode> testNode);*/

    @Query("CREATE (n:TestNode {name: :#{#testNode.name}}) RETURN n")
    TestNode createTestNode(TestNode testNode);

   /* @Query("CREATE (n:TestNode {name: :#{#testNode.name}) RETURN n")
    TestNode createTestNode(TestNode testNode);*/
}
