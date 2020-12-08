package com.dissertation.backend.repository;

import com.dissertation.backend.node.CandidateNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "candidateNode", path = "candidate_node")
public interface CandidateNodeRepository extends Neo4jRepository<CandidateNode, Long> {

    CandidateNode getCandidateNodeByName(@RequestParam("name") String name);

    Optional<CandidateNode> findCandidateNodeByEntityId(Long entity_id);

    @Query("MATCH (c:CandidateNode {entity_id: $candidate_id}), " +
            "(e:ExperienceNode {experience_id: $exp_id})" +
            "MERGE (c)-[r:EXPERIENCE]->(e);")
    void createRelationCandidateExperience(Long candidate_id, String exp_id);

    @Query("MATCH (c:CandidateNode {id: $candidate_id}), " +
            "(e:EducationNode {id: $edu_id})" +
            "MERGE (c)-[r:EDUCATION]->(e) RETURN c, r, e")
    void createRelationCandidateEducation(Long candidate_id, Long edu_id);


/*
    @Query("MATCH (c:CandidateNode)-[:KNOWS]-> (:Skill{name:'PHP'}) RETURN c")
    List<CandidateNode> salalala();
*/





}
