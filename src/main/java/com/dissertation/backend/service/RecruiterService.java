package com.dissertation.backend.service;

import com.dissertation.backend.exception.custom.candidate_exception.CandidateNotFoundException;
import com.dissertation.backend.model.SkillEntityModel;
import com.dissertation.backend.node.CandidateModel;
import com.dissertation.backend.node.JobNode;
import com.dissertation.backend.node.RecruiterNode;
import com.dissertation.backend.repository.CandidateNodeRepository;
import com.dissertation.backend.repository.RecruiterNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class RecruiterService {

    @Qualifier("graphDriver")
    private final Neo4jClient neo4jClient;

    private final RecruiterNodeRepository recruiterNodeRepository;
    private final CandidateNodeRepository candidateNodeRepository;

    public Set<JobNode> getAllJobsRecruiterManages(Long recruiterId){
        Optional<RecruiterNode> recruiter = this.recruiterNodeRepository.findByEntityId(recruiterId);
        if(recruiter.isPresent()){
            return recruiter.get()
                    .getJobNodeAdvertisements();
        }
        else{
            throw new CandidateNotFoundException("Candidate does not exist " + recruiterId);
        }
    }

    public Set<CandidateModel> getCandidateListFromSkillList(
            Long recruiterId, SkillEntityModel[] skillEntityModel){
        Optional<RecruiterNode> recruiter = this.recruiterNodeRepository.findByEntityId(recruiterId);
        if(recruiter.isPresent()){

            Map<String,Object> params = new HashMap<>();
            List<Map<String, Long>> list = new ArrayList<>();

            for(SkillEntityModel sk : asList(skillEntityModel)){
                Map<String, Long> n1 = new HashMap<>();
                SkillEntityModel skm =
                        new SkillEntityModel(sk.getId(), sk.getEntityId(), sk.getName(), sk.getYearsOfExperience());
                n1.put("id", sk.getId());
                n1.put("entity_id", sk.getEntityId());
                n1.put("yearsOfExperience", (long) sk.getYearsOfExperience());
                list.add(n1);
            }
            params.put("props", list);

            return new HashSet<CandidateModel>(neo4jClient.query(
                    " WITH  $props  AS rows " +
                            " UNWIND rows AS row " +
                            " MATCH  (s:SkillNode) " +
                            " WHERE s.entity_id = row.entity_id " +
                            " WITH  collect(row) as rows, collect(s) as allSkills " +
                            " UNWIND range(0, size(rows)-1) AS i" +
                            " WITH rows[i] AS row, allSkills[i] AS s,allSkills " +
                            " MATCH (c:CandidateNode)-[r:KNOWS]->(s:SkillNode) " +
                            " WHERE r.years_of_experience >= row.yearsOfExperience AND s in allSkills " +
                            " WITH c, collect(distinct r) as rels, collect(distinct s) as skills, allSkills " +
                            " WHERE ALL(sk in allSkills where sk in skills) " +
                            " RETURN c.entity_id as entityId, c.name as name, c.email as email, c.working_years as working_years;"
            )
                    .bind(params.get("props"))
                    .to("props")
                    .fetchAs(CandidateModel.class)
                    .mappedBy((typeSystem, record) -> {
                        return new CandidateModel(
                            record.get("entityId").asInt(),
                            //record.get("working_years").asInt(),
                            record.get("name").asString(),
                            record.get("email").asString()
                    );})
                    .all());


        }
        return null;
    }

}




/*
" WITH  $props  AS rows " +
" UNWIND rows AS row " +
" MATCH  (s:SkillNode) " +
" WHERE s.entity_id = row.entity_id " +
" WITH  collect(row) as rows, collect(s) as allSkills " +
" UNWIND rows as row " +
" MATCH (c:CandidateNode)-[r:KNOWS]->(s:SkillNode) " +
" WHERE r.years_of_experience >= row.yearsOfExperience AND s in allSkills " +
" WITH c, collect(distinct r) as rels, collect(distinct s) as skills, allSkills " +
" WHERE ALL(sk in allSkills where sk in skills) " +
" RETURN c.entity_id as entityId, c.name as name, c.email as email, c.working_years as working_years;"
*/
