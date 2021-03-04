package com.dissertation.backend.service;

import com.dissertation.backend.exception.custom.candidate_exception.CandidateNotFoundException;
import com.dissertation.backend.exception.custom.job_exception.JobNotFoundException;
import com.dissertation.backend.node.*;
import com.dissertation.backend.repository.CandidateNodeRepository;
import com.dissertation.backend.repository.JobNodeRepository;
import com.dissertation.backend.repository.RecruiterNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JobService {

    private final JobNodeRepository jobNodeRepository;
    private final RecruiterNodeRepository recruiterNodeRepository;
    private final CandidateNodeRepository candidateNodeRepository;

    @Qualifier("graphDriver")
    private final Neo4jClient neo4jClient;

    public JobNode setJob(Long recruiterId, JobNode jobNode) {
        Optional<RecruiterNode> recruiterNode = this.recruiterNodeRepository.findByEntityId(recruiterId);

        if (recruiterNode.isPresent()) {
            jobNode.setJobId(UUID.randomUUID().toString());
            recruiterNode.get().getJobNodeAdvertisements().add(jobNode);
            this.recruiterNodeRepository.save(recruiterNode.get());
            List<JobNode> jobNodeSkillRelationship = new ArrayList<>();

            jobNode.getRequiredSkills().forEach(jobRequiresRelationship -> {
                jobRequiresRelationship.setRelUuid(UUID.randomUUID().toString());
                jobNodeSkillRelationship.add(jobNode);
            });

            this.jobNodeRepository.saveAll(jobNodeSkillRelationship);
            Optional<JobNode> jbid = this.jobNodeRepository.findByJobId(jobNode.getJobId());
            return jbid.orElse(null);
        } else {
            return null;
        }
    }

    public Set<JobNode> getJobList(Long recruiterId) {
        Optional<RecruiterNode> recruiterNode = this.recruiterNodeRepository.findById(recruiterId);
        if (recruiterNode.isPresent()) {
            return recruiterNode.map(RecruiterNode::getJobNodeAdvertisements).orElse(null);
            // return recruiterNode.get().getJobNodeAdvertisements();
        } else {
            return null;
        }
    }

    public JobNode getJobByJobId(String jobId) {
        Optional<JobNode> jobNode = this.jobNodeRepository.findByJobId(jobId);
        return jobNode.orElse(null);
    }

    public JobNode updateJob(String jobId, JobNode jobNodeParam) {
        Optional<JobNode> jobNode = jobNodeRepository.findByJobId(jobId);
        if (jobNode.isPresent()) {
            JobNode job = jobNode.get();
            for(JobRequiresRelationship jrr : job.getRequiredSkills()){
                Long entityId = jrr.getSkillNode().getEntityId();
                jobNodeRepository.detatchRelationshipJobSkill(jobId, entityId);
            }

            updateJobObject(jobNodeParam, job);

            return jobNodeRepository.save(job);
        } else {
            return null;
        }
    }

    /**
     * @param jobId - Job Id
     */
    public boolean deleteJob(Long recruiterId, String jobId) {
        RecruiterNode recruiterNode = this.findRecruiterNodeByEntityId(recruiterId);
        Optional<JobNode> jobNode = jobNodeRepository.findByJobId(jobId);
        if (jobNode.isPresent()) {
            jobNodeRepository.delete(jobNode.get());
            Optional<JobNode> jobNodeExists = jobNodeRepository.findByJobId(jobId);
            return jobNodeExists.isPresent();
        } else {
            return false;
        }

    }

    public Boolean candidateApplyForJob(Long candidateId, String jobId) {
        Optional<JobNode> job = jobNodeRepository.findByJobId(jobId);
        Optional<CandidateNode> candidate = candidateNodeRepository.findCandidateNodeByEntityId(candidateId);

        if (job.isPresent() && candidate.isPresent()) {
            String uuid = UUID.randomUUID().toString();
            candidate.get().getJobNodes().add(new CandidateAppliedForJob(job.get(), uuid));
            CandidateNode cn = candidateNodeRepository.save(candidate.get());
            return cn.getJobNodes().stream()
                    .anyMatch(candidateAppliedForJob -> candidateAppliedForJob.getRelUuid().equals(uuid));
        }
        else {
           return false;
        }
    }

    public Boolean candidateDeleteApplyForJob(Long candidateId, String jobId) {
        Optional<JobNode> job = jobNodeRepository.findByJobId(jobId);
        Optional<CandidateNode> candidate = candidateNodeRepository.findCandidateNodeByEntityId(candidateId);

        if (job.isPresent() && candidate.isPresent()) {

            Set<CandidateAppliedForJob> a = candidate.get().getJobNodes().stream()
                    .peek(CandidateAppliedForJob::getJobNode)
                    .filter(s->s.getJobNode().getJobId().equals(jobId))
                    .collect(Collectors.toSet());
            String relUuid = a.stream().findFirst().get().getRelUuid();

            candidate.get().getJobNodes().removeIf(candidateAppliedForJob -> candidateAppliedForJob.getRelUuid().equals(relUuid));

            candidateNodeRepository.deleteByRelUuidIn(candidateId, relUuid);
            Optional<Boolean> isExist = candidateNodeRepository.checkIfRelationshipExists(relUuid);
            return isExist.isPresent();
        }
        else {
            return false;
        }
    }

    public boolean candidateHasAlreadyAppliedForJob(Long candidateId, String jobId) {
        Optional<JobNode> job = jobNodeRepository.findByJobId(jobId);
        Optional<CandidateNode> candidate = candidateNodeRepository.findCandidateNodeByEntityId(candidateId);

        if (job.isPresent() && candidate.isPresent()) {
            return candidate.get().getJobNodes().stream()
                    .anyMatch(candidateAppliedForJob -> candidateAppliedForJob.getJobNode().getJobId().equals(jobId));
        }
        else {
            return false;
        }
    }

    public Page<JobNode> candidateSearchJobByKeywords(List<String> keywords, int page, int size){
        String[] a = keywords.stream().map(s -> s.replace("%20", " ")).toArray(String[]::new);

        return this.jobNodeRepository.findJobNodeByListOfSkills(keywords.stream().map(s -> s.replace("%20", " ")).toArray(String[]::new), PageRequest.of(page-1,size));
              //  PageRequest.of(page, size)).getContent();
    }

    public int countCandidateSearchJobByKeywords(List<String> keywords){
        String[] a = keywords.stream().toArray(String[]::new);
        return this.jobNodeRepository.countJobNodeByListOfSkills(keywords.stream().toArray(String[]::new));
    }

    public RecruiterNode getRecruiterByJobId(String jobId){
        Optional<JobNode> jobNode = this.jobNodeRepository.findByJobId(jobId);
        if(jobNode.isPresent()){
            return this.recruiterNodeRepository.findRecruiterByJobId(jobId);
        }
        else{
            throw new JobNotFoundException("Job did not found " + jobId);
        }
    }

    public Set<JobNode> getAllJobsCandidateApplied(Long candidateId){
        Optional<CandidateNode> candidate = this.candidateNodeRepository.findCandidateNodeByEntityId(candidateId);
        if(candidate.isPresent()){
            return candidate.get().getJobNodes().stream()
                    .map(CandidateAppliedForJob::getJobNode)
                    .collect(Collectors.toSet());
        }
        else{
            throw new CandidateNotFoundException("Candidate does not exist " + candidateId);
        }
    }

    public Set<RecommendationExtendedModel> getRecommendationsForJob(String jobId, Long recruiterId){
        boolean check = jobNodeRepository.chechJobAndRecruiterRelationExists(jobId, recruiterId);
        if(check){
            return new HashSet<>(neo4jClient.query(
                    "MATCH (job:JobNode{job_id:$jobId})-[:REQUIRES]->(sk:SkillNode) " +
                            "WITH job, count(sk) as total_skills, collect(sk.name) as total_skill_names " +
                            "MATCH (job)-[r:REQUIRES]->(s:SkillNode)<-[k:KNOWS]-(c:CandidateNode) " +
                            "WHERE r.years_of_experience <= k.years_of_experience " +
                            "WITH c as candidate, count(s) as skills, total_skills, total_skill_names, collect(s.name) as haveSkillNames " +
                            "RETURN candidate.name as candidateName, candidate.entity_id as candidateEntityId, " +
                            "round(10^2*skills/total_skills)/10^2 as percent, " +
                            "skills as candidateSkillNumber, total_skills as totalSkillsNumber, " +
                            "total_skill_names as totalSkillNames, haveSkillNames " +
                            "ORDER BY percent DESC;"
            )
                    .bind(jobId)
                    .to("jobId")
                    .fetchAs(RecommendationExtendedModel.class)
                    .mappedBy((typeSystem, record) -> {
                        List<Object> haveSkillNames = record.get("haveSkillNames").asList();
                        List<Object> totalSkillNames = record.get("totalSkillNames").asList();
                        return new RecommendationExtendedModel(
                                record.get("candidateName").asString(),
                                record.get("candidateEntityId").asLong(),
                                haveSkillNames.toArray(new String[haveSkillNames.size()]),
                                totalSkillNames.toArray(new String[totalSkillNames.size()]),
                                record.get("percent").asDouble(),
                                record.get("totalSkillsNumber").asInt(),
                                record.get("candidateSkillNumber").asInt()
                        );
                    })
                    .all());
        }
        else {
            throw new JobNotFoundException("Job did not found " + jobId);
        }
    }

    public Set<RecommendationExtendedModel> getRecommendationsForAppliedJob(String jobId, Long recruiterId){
        boolean check = jobNodeRepository.chechJobAndRecruiterRelationExists(jobId, recruiterId);
        if(check){
            return new HashSet<>(neo4jClient.query(
                    "MATCH (job:JobNode{job_id:$jobId})-[:REQUIRES]->(sk:SkillNode) " +
                            "WITH job, count(sk) as total_skills, collect(sk.name) as total_skill_names " +
                            "MATCH (job)-[r:REQUIRES]->(s:SkillNode)<-[k:KNOWS]-(c:CandidateNode) " +
                            "MATCH (c)-[:APPLIED_FOR]-(job) " +
                            "WHERE r.years_of_experience <= k.years_of_experience " +
                            "WITH c as candidate, count(s) as skills, total_skills, total_skill_names, collect(s.name) as haveSkillNames " +
                            "RETURN candidate.name as candidateName, candidate.entity_id as candidateEntityId, " +
                            "round(10^2*skills/total_skills)/10^2 as percent, " +
                            "skills as candidateSkillNumber, total_skills as totalSkillsNumber, " +
                            "total_skill_names as totalSkillNames, haveSkillNames " +
                            "ORDER BY percent DESC;"
            )
                    .bind(jobId)
                    .to("jobId")
                    .fetchAs(RecommendationExtendedModel.class)
                    .mappedBy((typeSystem, record) -> {
                        List<Object> haveSkillNames = record.get("haveSkillNames").asList();
                        List<Object> totalSkillNames = record.get("totalSkillNames").asList();
                        return new RecommendationExtendedModel(
                                record.get("candidateName").asString(),
                                record.get("candidateEntityId").asLong(),
                                haveSkillNames.toArray(new String[haveSkillNames.size()]),
                                totalSkillNames.toArray(new String[totalSkillNames.size()]),
                                record.get("percent").asDouble(),
                                record.get("totalSkillsNumber").asInt(),
                                record.get("candidateSkillNumber").asInt()
                        );
                    })
                    .all());
        }
        else {
            throw new JobNotFoundException("Job did not found " + jobId);
        }
    }

    public String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    private RecruiterNode findRecruiterNodeByEntityId(Long recruiterId) {
        Optional<RecruiterNode> recruiterNode = this.recruiterNodeRepository.findByEntityId(recruiterId);
        if (recruiterNode.isPresent()) {
            return recruiterNode.get();
        } else {
            throw new NotFoundException();
        }
    }

    private void updateJobObject(JobNode jobNode, JobNode job) {
        if(!jobNode.getJobTitle().equals(job.getJobTitle())){
            job.setJobTitle(jobNode.getJobTitle());
        }
        if(!jobNode.getDescription().equals(job.getDescription())){
            job.setDescription(jobNode.getDescription());
        }
        if(jobNode.getRequiredSkills().size()>0 ){
            job.setRequiredSkills(jobNode.getRequiredSkills());
            job.getRequiredSkills().forEach(a -> {
                a.setRelUuid(UUID.randomUUID().toString());
            });
        }
    }

}
