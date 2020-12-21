package com.dissertation.backend.repository;

import com.dissertation.backend.node.EducationNode;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "educationNode", path = "education_node")
public interface EducationNodeRepository  extends Neo4jRepository<EducationNode, Long> {

    @Query("MATCH (e:ExperienceNode) <- [r:EDUCATION]-(c:CandidateNode) WHERE ID(c) = $id RETURN e")
    List<EducationNode> findCandidateEducation(Long id);

    @Transactional
    public default void deleteById(Long id) {

        Assert.notNull(id, "ID_MUST_NOT_BE_NULL");

        delete(findById(id).orElseThrow(() -> new EmptyResultDataAccessException(
                String.format("No entity with id %s exists!", id), 1)));
    }

    @Query("MATCH (e:EducationNode) WHERE e.education_id = $educationId DETACH DELETE e;")
    EducationNode deleteExperienceNodeByEducationIdCustom(String educationId);

    Optional<EducationNode> findByEducationId(String educationId);
}
