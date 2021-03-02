package com.dissertation.backend.service;

import com.dissertation.backend.entity.Candidate;
import com.dissertation.backend.exception.custom.candidate_exception.CandidateNotFoundException;
import com.dissertation.backend.repository.CandidateRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Utils {

    private final CandidateRepository candidateRepository;

    public Utils(CandidateRepository candidateRepository){
        this.candidateRepository = candidateRepository;
    }

    public boolean isRecruiter(Long id){
        Optional<Candidate> c = candidateRepository.findById(id);
        if(c.isPresent()){
            Candidate candidate = c.get();
            boolean contains = candidate.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("RECRUITER"));
            return contains;
        }
        else{
            throw new CandidateNotFoundException("Candidate not found!");
        }
    }

}
