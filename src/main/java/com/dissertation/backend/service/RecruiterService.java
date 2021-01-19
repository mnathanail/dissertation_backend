package com.dissertation.backend.service;

import com.dissertation.backend.exception.custom.candidate_exception.CandidateNotFoundException;
import com.dissertation.backend.node.JobNode;
import com.dissertation.backend.node.RecruiterNode;
import com.dissertation.backend.repository.RecruiterNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecruiterService {

    private final RecruiterNodeRepository recruiterNodeRepository;

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
}
