package com.dissertation.backend.service;

import com.dissertation.backend.entity.Candidate;
import com.dissertation.backend.entity.Role;
import com.dissertation.backend.enums.Roles;
import com.dissertation.backend.exception.custom.candidate_exception.CandidateNotFoundException;
import com.dissertation.backend.node.CandidateNode;
import com.dissertation.backend.node.RecruiterNode;
import com.dissertation.backend.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    private final CandidateNodeRepository candidateNodeRepository;

    private final ExperienceNodeRepository experienceNodeRepository;

    private final EducationNodeRepository educationNodeRepository;

    private final SummaryRepository summaryRepository;

    private final RoleRepository roleRepository;

    private final RecruiterNodeRepository recruiterNodeRepository;

    @Value("${company.email.domain}")
    private String emailCompanyDomain;

    public CandidateService(
            CandidateRepository candidateRepository,
            CandidateNodeRepository candidateNodeRepository,
            ExperienceNodeRepository experienceNodeRepository,
            EducationNodeRepository educationNodeRepository,
            SummaryRepository summaryRepository,
            RoleRepository roleRepository, RecruiterNodeRepository recruiterNodeRepository) {
        this.candidateRepository = candidateRepository;
        this.candidateNodeRepository = candidateNodeRepository;
        this.experienceNodeRepository = experienceNodeRepository;
        this.educationNodeRepository = educationNodeRepository;
        this.summaryRepository = summaryRepository;
        this.roleRepository = roleRepository;
        this.recruiterNodeRepository = recruiterNodeRepository;
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
        return candidate.orElseThrow(CandidateNotFoundException::new);
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

    public Candidate savePhotoProfile(Long candidateId, byte[] profilePic) {
        Optional<Candidate> c = candidateRepository.findById(candidateId);
        if (c.isPresent()) {
            Candidate candidate = c.get();
            candidate.setEmail(candidate.getEmail());
            candidate.setProfilePic(profilePic);
            return candidateRepository.save(candidate);
        } else {
            return null;
        }
    }

    public Candidate getProfile(Long id) {
        Optional<Candidate> candidate =  candidateRepository.findById(id);
        return candidate.orElse(null);
    }

    public Candidate register(Candidate candidate) {

        boolean isRecruiter = false;
        if (candidate.getEmail().contains(emailCompanyDomain)){
            Set<Role> roles = Stream.of(Roles.values())
                    .map(role ->
                            new Role(role.getKey(), role.getValue()))
                    .collect(Collectors.toSet());
            candidate.setAuthorities(roles);
            isRecruiter = true;
        }
        else{
            Set<Role> role = new HashSet<>();
            role.add(new Role(Roles.CANDIDATE.getKey(), Roles.CANDIDATE.getValue()));
            candidate.setAuthorities(role);
        }

        String uuid = UUID.randomUUID().toString();
        candidate.setCandidateId(uuid);
        Candidate c = candidateRepository.save(candidate);

        if (isRecruiter) {
            RecruiterNode rn = new RecruiterNode();
            rn.setEntityId(c.getId());
            rn.setRecruiterId(c.getCandidateId());
            rn.setEmail(c.getEmail());
            rn.setName(String.format("%s %s", c.getName(), c.getSurname()));
            this.recruiterNodeRepository.save(rn);
        }
        else{
            CandidateNode cn = new CandidateNode();
            cn.setEntityId(c.getId());
            cn.setCandidateId(c.getCandidateId());
            cn.setEmail(c.getEmail());
            cn.setName(String.format("%s %s", c.getName(), c.getSurname()));
            this.candidateNodeRepository.save(cn);
        }

        return c;
    }
}
