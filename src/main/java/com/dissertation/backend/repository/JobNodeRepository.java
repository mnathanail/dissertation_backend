package com.dissertation.backend.repository;

import com.dissertation.backend.node.JobNode;
import com.dissertation.backend.node.RecommendationExtendedModel;
import org.neo4j.driver.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "job", path = "job", excerptProjection = RecommendationExtendedModel.class)
public interface JobNodeRepository extends Neo4jRepository<JobNode, Long>
        //NonDomainResults
        {

    Optional<JobNode> findByJobId(String jobId);

    @Query("MATCH (job:JobNode{job_id:$jobId})<-[m:MANAGES]-(r:RecruiterNode{entity_id:$recruiterId}) return count(m)>0 as result;")
    boolean chechJobAndRecruiterRelationExists(String jobId, Long recruiterId);

    List<JobNode> findAllByJobId(Set<String> jobIds);

    @Query(value = "MATCH (j:JobNode)-[:REQUIRES]->(s:SkillNode) WHERE ANY (item IN $list WHERE s.name =~ '(?i)'+item) " +
            "WITH j  MATCH (j)-[r:REQUIRES]->(requiredSkills:SkillNode) " +
            "RETURN j, collect(j), collect(r), collect(requiredSkills) SKIP $skip LIMIT $limit",
            countQuery =
            "MATCH (j:JobNode)-[:REQUIRES]->(s:SkillNode) WHERE ANY (item IN $list WHERE s.name =~ '(?i)'+item)  " +
            "RETURN COUNT(j)")
    Page<JobNode> findJobNodeByListOfSkills(String[] list, Pageable pageable);

    @Query("MATCH (j:JobNode)-[:REQUIRES]->(s:SkillNode) WHERE ANY (item IN $list WHERE s.name =~ '(?i)'+item)  " +
            "RETURN COUNT(j)")
    int countJobNodeByListOfSkills(String[] list);

    @Query("MATCH (job:JobNode{job_id:$jobId})-[:REQUIRES]->(sk:SkillNode) " +
            "WITH job, count(sk) as total_skills, collect(sk.name) as total_skill_names " +
            "MATCH (job)-[r:REQUIRES]->(s:SkillNode)<-[k:KNOWS]-(c:CandidateNode) " +
            "where r.years_of_experience <= k.years_of_experience " +
            "WITH c as candidate, count(s) as skills, total_skills, total_skill_names " +
            "RETURN candidate.name as candidateName, candidate.entity_id as candidateEntityId, " +
            "round(10^2*skills/total_skills)/10^2 as percent " +

            "ORDER BY percent DESC;")
    Collection<Result> findRecommendedCandidatesForJob(String jobId);

    @Query("MATCH (job:JobNode {job_id: $jobId})-[r:REQUIRES]->(s:SkillNode{entity_id: $entityId}) DETACH DELETE r")
    void detatchRelationshipJobSkill(String jobId, Long entityId);


    @Query("UNWIND $rows AS row " +
            "MATCH  (s:SkillNode) " +
            " WHERE s.entity_id = row.entity_id " +
            " WITH  collect(row) as rows, collect(s) as allSkills " +
            " UNWIND rows as row " +
            " MATCH (c:CandidateNode)-[r:KNOWS]->(s:SkillNode) " +
            " WHERE r.years_of_experience >= row.yearsOfExperience AND s in allSkills " +
            " WITH c, collect(distinct r) as rels, collect(distinct s) as skills, allSkills " +
            " WHERE ALL(sk in allSkills where sk in skills) " +
            " RETURN c, rels, skills ")
            void lastQuery();




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









 @Query("MATCH (job:JobNode{job_id:$jobId})-[:REQUIRES]->(sk:SkillNode) " +
            "WITH job, count(sk) as total_skills, collect(sk.name) as total_skill_names " +
            "MATCH (job)-[r:REQUIRES]->(s:SkillNode)<-[k:KNOWS]-(c:CandidateNode) " +
            "where r.years_of_experience <= k.years_of_experience " +
            "WITH c as candidate, count(s) as skills, total_skills, total_skill_names, collect(s.name) as haveSkillNames " +
            "RETURN candidate.name as candidateName, candidate.entity_id as candidateEntityId, " +
            "round(10^2*skills/total_skills)/10^2 as percent, " +
            "skills as candidateSkillNumber, total_skills as totalSkillsNumber, " +
            "total_skill_names as totalSkillNames, haveSkillNames " +
            "ORDER BY percent DESC;")
*/

}
