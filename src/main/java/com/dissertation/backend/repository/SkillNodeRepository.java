package com.dissertation.backend.repository;

import com.dissertation.backend.node.CandidateSkillRelationship;
import com.dissertation.backend.node.SkillNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "skillNode", path = "skill_node")
public interface SkillNodeRepository extends Neo4jRepository<SkillNode, String> {

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

    List<SkillNode> findAllByEntityIdIn(List<Long> entity_id);

    @Query("MATCH (c:CandidateNode {entity_id:$entityId})-[r:KNOWS]->(:SkillNode) WHERE r.relUuid in $uuids DELETE r;")
    void deleteAllByRelUuidIn(Long entityId, List<String> uuids);

    @Query("MATCH (c:CandidateNode {entity_id: $entityId} -[r:KNOWS]->(:SkillNode) SET r.years_of_experience=$yoe RETURN r")
    CandidateSkillRelationship updateYearsOfExperience(Long entityId, Long yoe);
}

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
        "SET r+= row.props "

  MATCH (n:`SkillNode`) WHERE id(n) IN $__ids__ WITH n, id(n) AS __internalNeo4jId__
  RETURN n{.entity_id, .id, .name, __nodeLabels__: labels(n), __internalNeo4jId__: id(n)}

  MATCH (n:`SkillNode`) WHERE n.entity_id IN $entityId RETURN n{.entity_id, .id, .name, __nodeLabels__: labels(n), __internalNeo4jId__: id(n)}

 */
