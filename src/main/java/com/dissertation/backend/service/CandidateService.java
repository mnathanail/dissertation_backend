package com.dissertation.backend.service;

import com.dissertation.backend.entity.Candidate;
import com.dissertation.backend.node.CandidateNode;
import com.dissertation.backend.repository.*;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    private final CandidateNodeRepository candidateNodeRepository;

    private final ExperienceNodeRepository experienceNodeRepository;

    private final EducationNodeRepository educationNodeRepository;

    private final SummaryRepository summaryRepository;

    public CandidateService(
            CandidateRepository candidateRepository,
            CandidateNodeRepository candidateNodeRepository,
            ExperienceNodeRepository experienceNodeRepository,
            EducationNodeRepository educationNodeRepository,
            SummaryRepository summaryRepository
    ) {
        this.candidateRepository = candidateRepository;
        this.candidateNodeRepository = candidateNodeRepository;
        this.experienceNodeRepository = experienceNodeRepository;
        this.educationNodeRepository = educationNodeRepository;
        this.summaryRepository = summaryRepository;
    }


    public boolean saveCandidate(Candidate candidate) {
        Candidate save = candidateRepository.save(candidate);
        return save.getId() != null;
    }

    public Candidate checkIfUserExists(String email, String password) {
        Optional<Candidate> candidate = candidateRepository.findCandidateByEmailAndPassword(email, password);
        return candidate.orElseThrow(NotFoundException::new);
    }

    public Candidate findByEmail(String email) {
        Optional<Candidate> candidate = candidateRepository.findCandidateByEmail(email);
        return candidate.orElseThrow(NotFoundException:: new);
    }

    public CandidateNode saveOrUpdateCandidate(CandidateNode candidateNode) {
        CandidateNode c = candidateNodeRepository.save(candidateNode);
        return c;
    }

/*    public EducationNode saveEducation(EducationNode educationNode) {
        EducationNode edu = educationNodeRepository.save(educationNode);
        candidateNodeRepository.createRelationCandidateEducation(4L, 1L);
        return edu;
    }*/

    public Candidate savePhotoProfile(byte[] profilePic){
        Optional<Candidate> c = candidateRepository.findById(1L);
        if(c.isPresent()){

            Candidate candidate = c.get();
            candidate.setEmail(candidate.getEmail());
            candidate.setProfilePic(profilePic);

            return candidateRepository.save(candidate);
        }
        else {
            return c.orElseThrow(ArithmeticException::new);
        }
    }

    public Candidate getProfile(Long id) {
        return candidateRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }
}
