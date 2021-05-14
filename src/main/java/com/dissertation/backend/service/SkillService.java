package com.dissertation.backend.service;

import com.dissertation.backend.entity.Skill;
import com.dissertation.backend.exception.custom.candidate_exception.CandidateNotFoundException;
import com.dissertation.backend.exception.custom.recruiter_exception.RecruiterNotFoundException;
import com.dissertation.backend.node.*;
import com.dissertation.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final SkillNodeRepository skillNodeRepository;
    private final CandidateNodeRepository candidateNodeRepository;
    private final CandidateRepository candidateRepository;
    private final RecruiterNodeRepository recruiterNodeRepository;

    public List<Skill> getSkillLike(String skill) {
        return this.skillRepository.findFirst20ByNameContaining(skill);
    }

    public Set<CandidateSkillRelationship> getCandidateSkillList(Long candidateId) {
        boolean isRecruiter = this.isRecruiter(candidateId);
        if(!isRecruiter){
            Optional<CandidateNode> candidate = candidateNodeRepository.findCandidateNodeByEntityId(candidateId);
            return candidate.map(CandidateNode::getCandidateSkillRelationships).orElse(null);
        }
        else{
            Optional<RecruiterNode> candidate = recruiterNodeRepository.findByEntityId(candidateId);
            return candidate.map(RecruiterNode::getRecruiterSkillRelationships).orElse(null);
        }
    }

    public Skill setNewSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    public List<Skill> setNewSkillList(List<Skill> skills) {
        List<Skill> newSkills = this.skillRepository.saveAll(skills);
        return newSkills;
    }

    public Set<CandidateSkillRelationship> setSkills(Long candidateId, List<GeneralSkillNode> generalSkillsNode) {
        boolean isRecruiter = this.isRecruiter(candidateId);
        if(!isRecruiter) {
            CandidateNode candidate = candidateNodeRepository.findCandidateNodeByEntityId(candidateId)
                    .orElseThrow(() -> new CandidateNotFoundException("Candidate not found " + candidateId));
            Set<CandidateSkillRelationship> csr = generalSkillsNode.stream().map(gns -> {
                SkillNode sn = new SkillNode(gns.getName(), gns.getEntityId());
                return new CandidateSkillRelationship(
                        sn,
                        gns.getYearsOfExperience(),
                        UUID.randomUUID().toString()
                );
            }).collect(Collectors.toSet());

            if (!csr.isEmpty()) {
                candidate.getCandidateSkillRelationships().addAll(csr);
            } else {
                return null;
            }
            CandidateNode cn = this.candidateNodeRepository.save(candidate);
            if (!cn.getCandidateSkillRelationships().isEmpty())
                return cn.getCandidateSkillRelationships();
            else {
                return null;
            }
        }
        else{
            RecruiterNode recruiter = recruiterNodeRepository.findByEntityId(candidateId)
                    .orElseThrow(() -> new RecruiterNotFoundException("Recruiter not found " + candidateId));
            Set<CandidateSkillRelationship> csr = generalSkillsNode.stream().map(gns -> {
                SkillNode sn = new SkillNode(gns.getName(), gns.getEntityId());
                return new CandidateSkillRelationship(
                        sn,
                        gns.getYearsOfExperience(),
                        UUID.randomUUID().toString()
                );
            }).collect(Collectors.toSet());

            if (!csr.isEmpty()) {
                recruiter.getRecruiterSkillRelationships().addAll(csr);
            } else {
                return null;
            }
            RecruiterNode cn = this.recruiterNodeRepository.save(recruiter);
            if (!cn.getRecruiterSkillRelationships().isEmpty())
                return cn.getRecruiterSkillRelationships();
            else {
                return null;
            }
        }
    }

    public Set<CandidateSkillRelationship> updateRelationshipYoe(Long candidateId, List<CandidateSkillRelationship> candidateSkillRelationships) {
        boolean isRecruiter = this.isRecruiter(candidateId);
        if(!isRecruiter) {
            CandidateNode candidate = candidateNodeRepository.findCandidateNodeByEntityId(candidateId)
                    .orElseThrow(() -> new CandidateNotFoundException("Candidate not found " + candidateId));
            candidate.getCandidateSkillRelationships().forEach(myObject1 -> candidateSkillRelationships.stream()
                    .filter(myObject2 -> myObject1.getRelUuid().equals(myObject2.getRelUuid()))
                    .findAny().ifPresent(myObject2 -> myObject1.setYearsOfExperience(myObject2.getYearsOfExperience())));
            CandidateNode candidateNode = this.candidateNodeRepository.save(candidate);
            if (!candidateNode.getCandidateSkillRelationships().isEmpty())
                return candidateNode.getCandidateSkillRelationships();
            else {
                return null;
            }
        }
        else{
            RecruiterNode candidate = recruiterNodeRepository.findByEntityId(candidateId)
                    .orElseThrow(() -> new CandidateNotFoundException("Candidate not found " + candidateId));
            candidate.getRecruiterSkillRelationships().forEach(myObject1 -> candidateSkillRelationships.stream()
                    .filter(myObject2 -> myObject1.getRelUuid().equals(myObject2.getRelUuid()))
                    .findAny().ifPresent(myObject2 -> myObject1.setYearsOfExperience(myObject2.getYearsOfExperience())));
            RecruiterNode candidateNode = this.recruiterNodeRepository.save(candidate);
            if (!candidateNode.getRecruiterSkillRelationships().isEmpty())
                return candidateNode.getRecruiterSkillRelationships();
            else {
                return null;
            }
        }
    }

    public boolean deleteRelationshipCandidateSkill(Long candidateId, String uuid) {
        boolean isRecruiter = this.isRecruiter(candidateId);
        if(!isRecruiter) {
            CandidateNode candidate = candidateNodeRepository.findCandidateNodeByEntityId(candidateId)
                    .orElseThrow(() -> new CandidateNotFoundException("Candidate not found " + candidateId));
            this.skillNodeRepository.deleteAllByRelUuidIn(candidateId, uuid);
            Optional<Boolean> isDeleted = this.skillNodeRepository.checkIfRelationshipExists(uuid);
            return isDeleted.isPresent();
        }
        else{
            RecruiterNode recruiter = recruiterNodeRepository.findByEntityId(candidateId)
                    .orElseThrow(() -> new RecruiterNotFoundException("Recruiter not found " + candidateId));
            this.skillNodeRepository.deleteAllByRelUuidInRec(candidateId, uuid);
            Optional<Boolean> isDeleted = this.skillNodeRepository.checkIfRelationshipExists(uuid);
            return isDeleted.isPresent();
        }
    }

    private boolean isRecruiter(Long id){
        Utils util = new Utils(candidateRepository);
        return util.isRecruiter(id);
    }
}
