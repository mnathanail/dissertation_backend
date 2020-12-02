package com.dissertation.backend.repository;

import com.dissertation.backend.node.SkillNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "skillNode", path = "skill_node")
public interface SkillNodeRepository extends Neo4jRepository<SkillNode, Long> {

    @Query(
            "UNWIND $skills as row " +
            "MATCH (c:CandidateNode {entity_id:$id}), (s:SkillNode) WHERE s.entity_id IN row.id " +
            "MERGE (c)-[r:KNOWS]->(s) " +
            "SET r+= row.props "
    )
    void createRelationCandidateSkills(Long id, List<SkillNode> skills);

    @Query("MERGE (n:SkillNode {name: $skills.name, entity_id: $skills.entityId})")
    SkillNode createSkillNode(SkillNode skills);


    @Query("MATCH (c:CandidateNode {entity_id: $id})-[r:KNOWS]->(s:Skill) RETURN s")
    List<SkillNode> findSkillsByCandidateEntityId(Long id);
}
//{years_of_experience: $row.years_of_experience}
//MATCH (c:CandidateNode {id:4}), (s:Skill {id: 124}) MERGE (c)-[r:KNOWS] -> (s) RETURN c, r, s;

/*

"UNWIND $skills as row " +
        "MERGE (c:CandidateNode {entity_id:$id}), (s:Skill) WHERE s.entity_id IN row.id " +
        "MERGE (c)-[r:KNOWS]->(s) " +
        "SET r+= row.props " +
        "RETURN s"
        void createRelationCandidateSkills(Long id, List<SkillNode> skills);*/

/*
"UNWIND $skills as row " +
        "MATCH (c:CandidateNode {entity_id:$id}), (s:SkillNode) WHERE s.entity_id IN row.id " +
        "MERGE (c)-[r:KNOWS]->(s) " +
        "SET r+= row.props "*/
