package com.dissertation.backend.node;

import com.dissertation.backend.repository.NonDomainResults;
import org.springframework.data.neo4j.core.Neo4jClient;

import java.util.Collection;

class NonDomainResultsImpl implements NonDomainResults {

    private final Neo4jClient neo4jClient;

    NonDomainResultsImpl(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    @Override
    public Collection<Result> findRecommendedCandidatesForJob(String jobId) {
        return this.neo4jClient
                .query(""+
                "MATCH (job:JobNode{job_id:$jobId})-[:REQUIRES]->(sk:SkillNode) " +
                "WITH job, count(sk) as total_skills, collect(sk.name) as total_skill_names " +
                "MATCH (job)-[r:REQUIRES]->(s:SkillNode)<-[k:KNOWS]-(c:CandidateNode) " +
                "where r.years_of_experience <= k.years_of_experience " +
                "WITH c as candidate, count(s) as skills, total_skills, total_skill_names " +
                "RETURN candidate.name as candidateName, candidate.entity_id as candidateEntityId, " +
                "round(10^2*skills/total_skills)/10^2 as percent " +
                "ORDER BY percent DESC;"
                )

                .fetchAs(Result.class)
                .mappedBy((typeSystem, record) -> new Result(
                        record.get("candidateName").asString(),
                        record.get("candidateEntityId").asString(),
                        record.get("percent").asString()
                        )
                )
                .all();
    }
}