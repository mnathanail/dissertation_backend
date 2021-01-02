package com.dissertation.backend.repository;

import com.dissertation.backend.node.JobNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "job", path = "job")
public interface JobNodeRepository extends Neo4jRepository<JobNode, Long> {

    Optional<JobNode> findByJobId(String jobId);

    @Query("MATCH (e:JobNode) WHERE e.job_id = $jobId DETACH DELETE e;")
    JobNode deleteJobNodeByJobIdCustom(String jobId);

    @Query("MATCH (j:JobNode) -[:REQUIRES]->(s:SkillNode) WHERE  ANY (item IN $list WHERE s.name =~ '(?i)'+item)  " +
            "WITH j  MATCH (j)-[r:REQUIRES]->(requiredSkills:SkillNode) " +
            "RETURN j, collect(j), collect(r), collect(requiredSkills)")
    List<JobNode> findJobNodeByListOfSkills(String[] list);


    /*Match (j:JobNode) - [r:REQUIRES] -> (s:SkillNode) WHERE ANY (item IN $list WHERE s.name =~ '(?i)'+item) RETURN *;
    @Query (value="Match (j:JobNode) - [r:REQUIRES] -> (s:SkillNode) WHERE ANY (item IN $list WHERE j.name =~ '(?i)'+item) RETURN j",
            countQuery="Match (j:JobNode) - [r:REQUIRES] -> (s:SkillNode) WHERE ANY (item IN $list WHERE j.name =~ '(?i)'+item) RETURN count(j)")
    Page<JobNode> findJobNodeByListOfSkills(Pageable pageable, String[] list);

    MATCH (n:`JobNode`) WITH n, id(n) AS __internalNeo4jId__ RETURN
    n{.description, .id, .jobTitle, .job_id, __nodeLabels__: labels(n),
    __internalNeo4jId__: id(n),
    JobNode_REQUIRES_SkillNode:
    [(n)-[__relationship__:`REQUIRES`]->(n_requiredSkills:`SkillNode`)
    | n_requiredSkills{.entity_id, .name, __nodeLabels__: labels(n_requiredSkills),
    __internalNeo4jId__: id(n_requiredSkills), __relationship__}]}

*/

}
