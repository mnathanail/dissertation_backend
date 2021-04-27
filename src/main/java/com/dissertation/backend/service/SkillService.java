package com.dissertation.backend.service;

import com.dissertation.backend.entity.Skill;
import com.dissertation.backend.exception.custom.candidate_exception.CandidateNotFoundException;
import com.dissertation.backend.node.CandidateNode;
import com.dissertation.backend.node.CandidateSkillRelationship;
import com.dissertation.backend.node.GeneralSkillNode;
import com.dissertation.backend.node.SkillNode;
import com.dissertation.backend.repository.CandidateNodeRepository;
import com.dissertation.backend.repository.CandidateRepository;
import com.dissertation.backend.repository.SkillNodeRepository;
import com.dissertation.backend.repository.SkillRepository;
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

    public List<Skill> getSkillLike(String skill) {
        return this.skillRepository.findFirst20ByNameContaining(skill);
    }

    public Set<CandidateSkillRelationship> getCandidateSkillList(Long candidateId) {
        Optional<CandidateNode> candidate = candidateNodeRepository.findCandidateNodeByEntityId(candidateId);
        return candidate.map(CandidateNode::getCandidateSkillRelationships).orElse(null);
    }

    public Skill setNewSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    public List<Skill> setNewSkillList(List<Skill> skills) {
        List<Skill> newSkills = this.skillRepository.saveAll(skills);

       /* List<com.dissertation.backend.node_entity.Skill> skillsNode = new ArrayList<>();

        newSkills.forEach(skill -> {
            com.dissertation.backend.node_entity.Skill sne  = new com.dissertation.backend.node_entity.Skill();
            sne.setId(skill.getId());
            sne.setName(skill.getName());
            skillsNode.add(sne);
        });

        List<com.dissertation.backend.node_entity.Skill> nodeSkills = skillNodeRepository.saveAll(skillsNode);

        skillNodeRepository.createRelationCandidateSkills(candidateId, nodeSkills);*/
        return newSkills;
    }

    public Set<CandidateSkillRelationship> setSkills(Long candidateId, List<GeneralSkillNode> generalSkillsNode) {

        List<SkillNode> skillNodes = new ArrayList<>();

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

        if(!csr.isEmpty()){
            candidate.getCandidateSkillRelationships().addAll(csr);
        }
        else{
            return null;
        }


        CandidateNode cn =  this.candidateNodeRepository.save(candidate);
        if(!cn.getCandidateSkillRelationships().isEmpty())
            return cn.getCandidateSkillRelationships();
        else{
            return null;
        }

       /* for (GeneralSkillNode generalSkillNode : generalSkillsNode) {
            SkillNode skillNode = new SkillNode();
            BeanUtils.copyProperties(
                    generalSkillNode,
                    skillNode,
                    Long.toString(generalSkillNode.getYearsOfExperience())
            );
            skillNodes.add(skillNode);
        }



        List<SkillNode> savedSkillNodes = this.skillNodeRepository.saveAll(skillNodes);
        List<CandidateNode> candidateSkillRelationship = new ArrayList<>();

        *//*candidate.getCandidateSkillRelationships().stream().filter(c-> c.getSkillNode().getName().equals("manos"))
                .findFirst().map(c -> candidate.getCandidateSkillRelationships().remove(c));*//*

        IntStream
                .range(0, savedSkillNodes.size())
                .forEach(index -> {
                    if (savedSkillNodes.get(index).getEntityId().equals(generalSkillsNode.get(index).getEntityId())) {
                        SkillNode value = savedSkillNodes.get(index);
                        boolean isPresent = candidate.getCandidateSkillRelationships().stream()
                                .anyMatch(o -> o.getSkillNode().getName().equals(value.getName()));
                        if (!isPresent) {
                            candidate
                                    .getCandidateSkillRelationships()
                                    .add(
                                            new CandidateSkillRelationship(
                                                    savedSkillNodes.get(index),
                                                    generalSkillsNode.get(index).getYearsOfExperience(),
                                                    UUID.randomUUID().toString()
                                            )
                                    );
                            candidateSkillRelationship.add(candidate);
                        }
                    }
                });


        List<CandidateNode> candidateNodeList = candidateNodeRepository.saveAll(candidateSkillRelationship);

        Set<CandidateSkillRelationship> candidateSkillsWithExperience = candidateNodeList.stream()
                .flatMap(c -> c.getCandidateSkillRelationships().stream()).collect(Collectors.toSet());

        return candidateSkillsWithExperience;*/
    }

    public Set<CandidateSkillRelationship> updateRelationshipYoe(Long candidateId, List<CandidateSkillRelationship> candidateSkillRelationships) {

        CandidateNode candidate = candidateNodeRepository.findCandidateNodeByEntityId(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found " + candidateId));


        candidate.getCandidateSkillRelationships().forEach(myObject1 -> candidateSkillRelationships.stream()
                .filter(myObject2 -> myObject1.getRelUuid().equals(myObject2.getRelUuid()))
                .findAny().ifPresent(myObject2 -> myObject1.setYearsOfExperience(myObject2.getYearsOfExperience())));

        CandidateNode candidateNode = this.candidateNodeRepository.save(candidate);

       /* IntStream
                .range(0, candidateSkillRelationships.size())
                .forEach(index -> {
                    CandidateSkillRelationship skillNode = candidateSkillRelationships.get(index);

                    boolean isPresent = candidate.getCandidateSkillRelationships().stream()
                            .anyMatch(o -> {
                                return o.getSkillNode().getName().equals(skillNode.getSkillNode().getName())
                                        && o.getRelUuid().equals(skillNode.getRelUuid());
                            });


                    if (isPresent) {
                        candidate.getCandidateSkillRelationships().forEach(c -> {
                            if (c.getRelUuid() != null) {
                                if (c.getRelUuid().equals(skillNode.getRelUuid())) {
                                    c.setYearsOfExperience(skillNode.getYearsOfExperience());
                                }
                            }
                        });
                    }
                });*/

        //CandidateNode candidateNode = this.candidateNodeRepository.save(candidate);

        if(!candidateNode.getCandidateSkillRelationships().isEmpty())
            return candidateNode.getCandidateSkillRelationships();
        else{
            return null;
        }
    }

    public boolean deleteRelationshipCandidateSkill(Long candidateId, String uuid) {
        CandidateNode candidate = candidateNodeRepository.findCandidateNodeByEntityId(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found " + candidateId));
        this.skillNodeRepository.deleteAllByRelUuidIn(candidateId, uuid);
        Optional<Boolean> isDeleted = this.skillNodeRepository.checkIfRelationshipExists(uuid);
        return isDeleted.isPresent();
    }

    private boolean isRecruiter(Long id){
        Utils util = new Utils(candidateRepository);
        return util.isRecruiter(id);
    }
}
