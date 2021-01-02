package com.dissertation.backend.service;

import com.dissertation.backend.node.CandidateAppliedForJob;
import com.dissertation.backend.node.CandidateNode;
import com.dissertation.backend.node.JobNode;
import com.dissertation.backend.node.RecruiterNode;
import com.dissertation.backend.repository.CandidateNodeRepository;
import com.dissertation.backend.repository.JobNodeRepository;
import com.dissertation.backend.repository.RecruiterNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class JobService {

    private final JobNodeRepository jobNodeRepository;
    private final RecruiterNodeRepository recruiterNodeRepository;
    private final CandidateNodeRepository candidateNodeRepository;

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
            return this.jobNodeRepository.findByJobId(jobNode.getJobId()).get();
        } else {
            throw new NotFoundException();
        }
    }

    public Set<JobNode> getJobList(Long recruiterId) {
        Optional<RecruiterNode> recruiterNode = this.recruiterNodeRepository.findById(recruiterId);
        if (recruiterNode.isPresent()) {
            return recruiterNode.get().getJobNodeAdvertisements();
        } else {
            throw new NotFoundException();
        }
    }

    public JobNode getJobByJobId(String jobId) {

        Optional<JobNode> jobNode = this.jobNodeRepository.findByJobId(jobId);
        if (jobNode.isPresent()) {
            return jobNode.get();
        } else {
            throw new NotFoundException();
        }
    }

    public JobNode updateJob(String jobId, JobNode jobNodeParam) {
        Optional<JobNode> jobNode = jobNodeRepository.findByJobId(jobId);
        if (jobNode.isPresent()) {
            JobNode job = jobNode.get();
            BeanUtils.copyProperties(jobNodeParam, job, getNullPropertyNames(jobNodeParam));
            return jobNodeRepository.save(job);
        } else {
            return jobNode.orElseThrow(ArithmeticException::new);
        }
    }

    /**
     * @param jobId - Job Id
     */
    public boolean deleteJob(Long recruiterId, String jobId) {
        RecruiterNode recruiterNode = this.findRecruiterNodeByEntityId(recruiterId);
        Optional<JobNode> jobNode = jobNodeRepository.findByJobId(jobId);
        if (jobNode.isPresent()) {
            jobNodeRepository.deleteJobNodeByJobIdCustom(jobId);
            Optional<JobNode> jobNodeExists = jobNodeRepository.findByJobId(jobId);
            return jobNodeExists.isPresent();
        } else {
            throw new NotFoundException();
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
            throw  new NotFoundException();
        }
    }

    public List<JobNode> candidateSearchJobByKeywords(List<String> keywords){
        String[] a = keywords.stream().toArray(String[]::new);


        List<JobNode> takis =
                this.jobNodeRepository.findJobNodeByListOfSkills(keywords.stream().toArray(String[]::new));
        return this.jobNodeRepository.findJobNodeByListOfSkills(keywords.stream().toArray(String[]::new));
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

}
