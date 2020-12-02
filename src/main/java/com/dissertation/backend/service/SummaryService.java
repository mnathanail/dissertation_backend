package com.dissertation.backend.service;

import com.dissertation.backend.entity.Candidate;
import com.dissertation.backend.entity.Summary;
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
    public Summary saveSummary(Summary summary) {
        Optional<Candidate> opt_candidate = candidateRepository.findById(1L);
        if (opt_candidate.isPresent()) {
            Candidate candidate = opt_candidate.get();
            candidate.setEmail(candidate.getEmail());
            summary.setCandidate(candidate);
            candidate.setSummary(summary);
            candidateRepository.save(candidate);
        } else {
            //see custom exceptions
            opt_candidate.orElseThrow(ArithmeticException::new);
        }
        return summary;
    }

    /**
     * @param id - Summary Id
     * @return summary|null
     */
    public Summary getSummary(long id) {
        Optional<Summary> summary = summaryRepository.findById(id);
        return summary.orElseThrow(ArithmeticException::new);
    }
}
