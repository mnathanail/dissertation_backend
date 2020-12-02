package com.dissertation.backend.service;

import com.dissertation.backend.entity.Skill;
import com.dissertation.backend.node.SkillNode;
import com.dissertation.backend.node.TestNode;
import com.dissertation.backend.repository.SkillNodeRepository;
import com.dissertation.backend.repository.SkillRepository;
import com.dissertation.backend.repository.TestNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final SkillNodeRepository skillNodeRepository;
    private final TestNodeRepository testNodeRepository;

    public List<Skill> getSkills(int size) {
        return this.skillRepository.findAll(PageRequest.of(1, size)).toList();
    }

    public List<Skill> getSkills() {
        return this.skillRepository.findAll(PageRequest.of(1, 20)).toList();
    }

    public List<Skill> getSkillLike(String skill) {
        return this.skillRepository.findFirst20ByNameContaining(skill);
    }

    public List<SkillNode> getCandidateSkillList(Long candidateId) {
        return this.skillNodeRepository.findSkillsByCandidateEntityId(candidateId);
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

    public void setSkills(Long id, List<SkillNode> skills) {


        TestNode testNode = new TestNode();
        testNode.setName("Takis");
        TestNode aaa = this.testNodeRepository.createTestNode(testNode);
        System.out.println(
                "Geia Ces!"
        );
        SkillNode a = this.skillNodeRepository.createSkillNode(skills.get(0));

       /* if (a.size() > 0) {

        }
        this.skillNodeRepository.createRelationCandidateSkills(id, skills);*/
        //
    }

}
