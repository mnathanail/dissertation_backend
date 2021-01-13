package com.dissertation.backend.repository;

import com.dissertation.backend.node.ExperienceNode;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "experienceNode", path = "experience_node")
public interface ExperienceNodeRepository  extends Neo4jRepository<ExperienceNode, Long> {

    @Query("MATCH (e:ExperienceNode) <- [r:EXPERIENCE]-(c:CandidateNode) WHERE c.entity_id = $id RETURN e")
    List<ExperienceNode> findCandidateExperience(Long id);

    @Transactional
    public default void deleteById(Long id) {

        Assert.notNull(id, "ID_MUST_NOT_BE_NULL");

        delete(findById(id).orElseThrow(() -> new EmptyResultDataAccessException(
                String.format("No entity with id %s exists!", id), 1)));
    }

    @Query("MATCH (e:ExperienceNode) WHERE e.experience_id = $experienceId DETACH DELETE e;")
    ExperienceNode deleteExperienceNodeByExperienceIdCustom(String experienceId);

    Optional<ExperienceNode> findByExperienceId(String experienceId);

}
