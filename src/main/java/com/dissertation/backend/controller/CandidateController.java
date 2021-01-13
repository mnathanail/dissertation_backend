package com.dissertation.backend.controller;

import com.dissertation.backend.entity.Candidate;
import com.dissertation.backend.entity.Skill;
import com.dissertation.backend.entity.Summary;
import com.dissertation.backend.node.CandidateSkillRelationship;
import com.dissertation.backend.node.EducationNode;
import com.dissertation.backend.node.ExperienceNode;
import com.dissertation.backend.node.GeneralSkillNode;
import com.dissertation.backend.repository.CandidateNodeRepository;
import com.dissertation.backend.repository.CandidateRepository;
import com.dissertation.backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RepositoryRestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
@Validated
public class CandidateController {

    private final CandidateService candidateService;
    private final ExperienceService experienceService;
    private final EducationService educationService;
    private final CandidateNodeService candidateNodeService;
    private final CandidateRepository candidateRepository;
    private final SummaryService summaryService;
    private final SkillService skillService;
    private final CandidateNodeRepository candidateNodeRepository;

    @PostMapping(value = "/candidate/login")
    public ResponseEntity<Candidate> login(@Valid @RequestParam Candidate candidate) {
        Candidate exists = candidateService.checkIfUserExists(candidate.getEmail(), candidate.getPassword());
        return ResponseEntity.ok(exists);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Candidate> register(@Valid @RequestParam Candidate candidate) {
        Candidate c = candidateService.register(candidate);
        return ResponseEntity.ok(c);
    }

    @GetMapping(value = "/find-by-email")
    public ResponseEntity<Candidate> findByEmail(@Email @RequestParam("email") String email) {
        Candidate candidate = candidateService.findByEmail(email);
        return ResponseEntity.ok(candidate);
    }

    /**
     * @param candidateId - Long
     * @param candidate - Candidate Obj
     * @return
     */
    @PostMapping(value = "/{candidateId}/save/photo-profile")
    public ResponseEntity<Candidate> setCandidatePhotoProfile(@PathVariable("candidateId") Long candidateId,
            @RequestBody Candidate candidate){
        Candidate c = candidateService.savePhotoProfile(candidateId, candidate.getProfilePic());
        return ResponseEntity.ok(c);
    }

    /**
     * @param id - Long
     * @return
     */
    @GetMapping(value = "/{candidateId}/get/profile")
    public ResponseEntity<Candidate> getCandidateProfile(@PathVariable("candidateId") Long id){
        Candidate c = candidateService.getProfile(id);
        return ResponseEntity.ok(c);
    }

    /**
     * @param summary
     * @return ResponseEntity<Summary>
     */
    @PostMapping(value = "/{candidateId}/save/summary")
    public ResponseEntity<Summary> setCandidateSummary(@PathVariable("candidateId") Long id,
                                                       @RequestBody Summary summary) {
        Summary sum = summaryService.saveSummary(id, summary);
        return ResponseEntity.ok(sum);
    }

    /**
     * @param id
     * @return ResponseEntity<Summary>
     */
    @GetMapping(value = "/{candidateId}/get/summary")
    public ResponseEntity<Optional<Summary>> getCandidateSummary(@PathVariable("candidateId") String id) {
        Optional<Summary> sum = summaryService.getSummary(Long.parseLong(id));
        return ResponseEntity.ok(sum);
    }
    /*----------------------------Experience--------------------------------------------------------------------------*/

    /**
     * @param experienceNode
     * @return ResponseEntity<ExperienceNode>
     */
    @PostMapping(value = "/{candidateId}/save/working-experience")
    public ResponseEntity<ExperienceNode> setCandidateWorkExperience(
            @Valid @RequestBody ExperienceNode experienceNode,
            @PathVariable("candidateId") Long id) {
        ExperienceNode exp = experienceService.setExperience(experienceNode, id);
        return ResponseEntity.ok(exp);
    }

    /**
     * @param candidateId
     * @return ResponseEntity<ExperienceNode>
     */
    @GetMapping(value = "/{candidateId}/get/working-experience/{experienceId}")
    public ResponseEntity<ExperienceNode> getCandidateWorkExperience(
            @PathVariable("candidateId") String candidateId,
            @PathVariable("experienceId") String experienceId) {
        ExperienceNode exp = experienceService.getExperience(experienceId);
        return ResponseEntity.ok(exp);
    }

    /**
     * @param candidateId
     * @return ResponseEntity<List<ExperienceNode>>
     */
    @GetMapping(value = "/{candidateId}/get/working-experience")
    public ResponseEntity<List<ExperienceNode>> getCandidateWorkExperienceList(
            @PathVariable("candidateId") Long candidateId) {
        List<ExperienceNode> exp = experienceService.getListExperience(candidateId);
        return ResponseEntity.ok(exp);
    }

    /**
     * @param experienceId
     * @return ResponseEntity<ExperienceNode>
     */
    @PatchMapping(value = "/{candidateId}/patch/working-experience/{experienceId}")
    public ResponseEntity<ExperienceNode> patchCandidateWorkExperience(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("experienceId") String experienceId,
            @RequestBody ExperienceNode experienceNode) {

        ExperienceNode exp =  experienceService.patchExperience(experienceId, experienceNode);
        return ResponseEntity.ok(exp);
    }

    /**
     * @param experienceId
     * @return ResponseEntity<Boolean>
     */
    @DeleteMapping(value = "/{id}/delete/working-experience/{experienceId}")
    public ResponseEntity<Boolean> deleteExperienceByExperienceId(@PathVariable("experienceId") String experienceId) {
        boolean deleteExperienceNode = experienceService.deleteExperience(experienceId);
        return ResponseEntity.ok(!deleteExperienceNode);
    }

    /*----------------------------/Experience-------------------------------------------------------------------------*/

    /*----------------------------Education---------------------------------------------------------------------------*/

    /**
     * @param educationNode
     * @return ResponseEntity<EducationNode>
     */
    @PostMapping(value = "/{candidateId}/save/education")
    public ResponseEntity<EducationNode> setCandidateEducation(
            @Valid @RequestBody EducationNode educationNode,
            @PathVariable("candidateId") Long id) {
        EducationNode ed = educationService.setEducation(educationNode, id);
        return ResponseEntity.ok(ed);
    }

    /**
     * @param candidateId
     * @return ResponseEntity<EducationNode>
     */
    @GetMapping(value = "/{candidateId}/get/education/{educationId}")
    public ResponseEntity<EducationNode> getCandidateEducation(
            @PathVariable("candidateId") String candidateId,
            @PathVariable("educationId") String educationId) {
        EducationNode ed = educationService.getEducation(educationId);
        return ResponseEntity.ok(ed);
    }

    /**
     * @param candidateId
     * @return ResponseEntity<Set<EducationNode>>
     */
    @GetMapping(value = "/{candidateId}/get/education")
    public ResponseEntity<Set<EducationNode>> getCandidateEducationList(@PathVariable("candidateId") Long candidateId) {
        Set<EducationNode> ed = educationService.getListEducation(candidateId);
        return ResponseEntity.ok(ed);
    }

    /**
     * @param educationId
     * @return ResponseEntity<EducationNode>
     */
    @PatchMapping(value = "/{candidateId}/patch/education/{educationId}")
    public ResponseEntity<EducationNode> patchCandidateEducation(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("educationId") String educationId,
            @RequestBody EducationNode educationNode) {

        EducationNode exp =  educationService.patchEducation(educationId, educationNode);
        return ResponseEntity.ok(exp);
    }

    /**
     * @param educationId
     * @return ResponseEntity<Boolean>
     */
    @DeleteMapping(value = "/{candidateId}/delete/education/{educationId}")
    public ResponseEntity<Boolean> deleteCandidateEducation(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("educationId") String educationId) {
        boolean deleteEducationNode = educationService.deleteEducation(educationId);
        return ResponseEntity.ok(!deleteEducationNode);
    }

    /*----------------------------/Education--------------------------------------------------------------------------*/

    /*----------------------------Skill-------------------------------------------------------------------------------*/



    /**
     * @param candidateId - Long
     * @return ResponseEntity<Set<CandidateSkillRelationship>>
     */
    @GetMapping("/{candidateId}/get/candidate-skill-list")
    public ResponseEntity<Set<CandidateSkillRelationship>> getCandidateSkillList(@PathVariable("candidateId") Long candidateId) {
        Set<CandidateSkillRelationship> skills = skillService.getCandidateSkillList(candidateId);
        return ResponseEntity.ok(skills);
    }

    @PostMapping("/{id}/save/skill")
    public ResponseEntity<Skill> setSearchSkill(@RequestBody Skill skill) {
        Skill newSkill = skillService.setNewSkill(skill);
        return ResponseEntity.ok(newSkill);
    }

    @PostMapping("/{id}/save/skill-list")
    public ResponseEntity<List<Skill>> setSearchListSkills(@RequestBody List<Skill> skill) {
        List<Skill> newSkill = skillService.setNewSkillList(skill);
        return ResponseEntity.ok(newSkill);
    }

    @PostMapping("/{id}/save/candidate-skill-list")
    public ResponseEntity<Set<CandidateSkillRelationship>> setNodeListSkills(
            @PathVariable("id") Long candidateId,
            @RequestBody List<GeneralSkillNode> skill) {

        Set<CandidateSkillRelationship> candidateNodeSet= skillService.setSkills(candidateId, skill);
        return ResponseEntity.ok(candidateNodeSet);
    }

    @PatchMapping("/{id}/patch/candidate-skill-list")
    public ResponseEntity<Set<CandidateSkillRelationship>> patchNodeListSkills(
            @PathVariable("id") Long candidateId,
            @RequestBody List<CandidateSkillRelationship> skill) {

        Set<CandidateSkillRelationship> candidateNodeSet= skillService.updateRelationshipYoe(candidateId, skill);
        return ResponseEntity.ok(candidateNodeSet);
    }

    /**
     * @param skillUuid
     * @return
     */
    @DeleteMapping(value = "/{id}/delete/candidate-skill/{skillUuid}")
    public ResponseEntity<Boolean> deleteSkillCandidateBySkillUuid(@PathVariable("id") Long candidateId,
                                                                   @PathVariable("skillUuid") String skillUuid) {
        Boolean deleteExperienceNode = skillService.deleteRelationshipCandidateSkill(candidateId, skillUuid);
        return ResponseEntity.ok(!deleteExperienceNode);
    }

    /*---------------------------/Skill-------------------------------------------------------------------------------*/

}
