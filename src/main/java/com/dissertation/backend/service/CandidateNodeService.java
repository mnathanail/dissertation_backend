package com.dissertation.backend.service;

import com.dissertation.backend.node.CandidateNode;
import com.dissertation.backend.node.ExperienceNode;
import com.dissertation.backend.repository.CandidateNodeRepository;
import com.dissertation.backend.repository.ExperienceNodeRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateNodeService {

    private final CandidateNodeRepository candidateNodeRepository;
    private final ExperienceNodeRepository experienceNodeRepository;

    public CandidateNodeService(CandidateNodeRepository candidateNodeRepository,
                                ExperienceNodeRepository experienceNodeRepository) {
        this.candidateNodeRepository = candidateNodeRepository;
        this.experienceNodeRepository = experienceNodeRepository;
    }


    /**
     * @param experienceNode save experience
     * @return ExperienceNode
     */
    public ExperienceNode saveExperience(ExperienceNode experienceNode) {
        ExperienceNode exp = experienceNodeRepository.save(experienceNode);
        System.out.println(exp);
        return exp;
    }


    public CandidateNode
    saveOrUpdateCandidate(CandidateNode candidateNode) {
        CandidateNode c = candidateNodeRepository.save(candidateNode);
        return c;
    }
}
