package com.dissertation.backend.service;

import com.dissertation.backend.entity.Skill;
import com.dissertation.backend.node.CandidateNode;
import com.dissertation.backend.node.CandidateSkillRelationship;
import com.dissertation.backend.node.GeneralSkillNode;
import com.dissertation.backend.node.SkillNode;
import com.dissertation.backend.repository.CandidateNodeRepository;
import com.dissertation.backend.repository.SkillNodeRepository;
import com.dissertation.backend.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final SkillNodeRepository skillNodeRepository;
    private final CandidateNodeRepository candidateNodeRepository;

    public List<Skill> getSkills(int size) {
        return this.skillRepository.findAll(PageRequest.of(1, size)).toList();
    }

    public List<Skill> getSkills() {
        return this.skillRepository.findAll(PageRequest.of(1, 20)).toList();
    }

    public List<Skill> getSkillLike(String skill) {
        return this.skillRepository.findFirst20ByNameContaining(skill);
    }

    public Set<CandidateSkillRelationship> getCandidateSkillList(Long candidateId) {
        CandidateNode candidate = candidateNodeRepository.findCandidateNodeByEntityId(candidateId)
                .orElseThrow(RuntimeException::new);

        return candidate.getCandidateSkillRelationships();
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

    @Transactional
    public Set<CandidateSkillRelationship> setSkills(Long id, List<GeneralSkillNode> generalSkillsNode) {

        List<SkillNode> skillNodes = new ArrayList<>();

        CandidateNode candidate = candidateNodeRepository.findCandidateNodeByEntityId(id)
                .orElseThrow(RuntimeException::new);

        for (GeneralSkillNode generalSkillNode : generalSkillsNode) {
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

        /*candidate.getCandidateSkillRelationships().stream().filter(c-> c.getSkillNode().getName().equals("manos"))
                .findFirst().map(c -> candidate.getCandidateSkillRelationships().remove(c));*/

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

/*
        candidate.getCandidateSkillRelationships().stream().filter(f ->f.getRelUuid().equals("603bb825-a8f7-4d64-b9f5-30dd44ee72f4"))
                .findFirst().map(c-> {
                        return new CandidateSkillRelationship(c.getSkillNode(), 123L, c.getRelUuid());
                });
*/

        CandidateNode cas = this.candidateNodeRepository.save(candidate);

        List<CandidateNode> candidateNodeList = candidateNodeRepository.saveAll(candidateSkillRelationship);

        Set<CandidateSkillRelationship> candidateSkillsWithExperience = candidateNodeList.stream()
                .flatMap(c -> c.getCandidateSkillRelationships().stream()).collect(Collectors.toSet());


        return candidateSkillsWithExperience;
    }

    public void deleteRelationshipCandidateSkill(Long id, List<CandidateSkillRelationship> candidateSkillRelationships){

        List<String> uuids = candidateSkillRelationships
                .stream()
                .map(CandidateSkillRelationship::getRelUuid)
                .collect(Collectors.toList());

        CandidateNode candidate = candidateNodeRepository.findCandidateNodeByEntityId(id)
                .orElseThrow(RuntimeException::new);
        this.skillNodeRepository.deleteAllByRelUuidIn(id, uuids);
    }

    public void updateRelationshipYoe(Long id,CandidateSkillRelationship candidateSkillRelationship, Long yoe){

        CandidateNode candidate = candidateNodeRepository.findCandidateNodeByEntityId(id)
                .orElseThrow(RuntimeException::new);

        String uuid = candidateSkillRelationship.getRelUuid();
        candidate.getCandidateSkillRelationships().forEach(c -> {
            if(c.getRelUuid() != null){
                if(c.getRelUuid().equals(uuid)){
                    c.setYoe(yoe);
                }
            }
        });

    }

}
