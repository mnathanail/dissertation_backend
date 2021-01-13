package com.dissertation.backend.service;

import com.dissertation.backend.entity.Candidate;
import com.dissertation.backend.entity.Summary;
import com.dissertation.backend.exception.custom.candidate_exception.CandidateNotFoundException;
import com.dissertation.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final CandidateRepository candidateRepository;
    private final SummaryRepository summaryRepository;

    /**
     * @param summary - Summary Obj
     * @return
     */
    public Summary saveSummary(Long candidateId, Summary summary) {
        Optional<Candidate> opt_candidate = candidateRepository.findById(candidateId);
        if (opt_candidate.isPresent()) {
            Candidate candidate = opt_candidate.get();
            candidate.setEmail(candidate.getEmail());
            summary.setCandidate(candidate);
            candidate.setSummary(summary);
            candidateRepository.save(candidate);
        } else {
            //see custom exceptions
            throw new CandidateNotFoundException("Candidate not found " + candidateId);
        }
        return summary;
    }

    /**
     * @param id - Summary Id
     * @return summary|null
     */
    public Optional<Summary> getSummary(long id) {
        return summaryRepository.findById(id);
    }
}
