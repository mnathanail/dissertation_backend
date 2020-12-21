package com.dissertation.backend.controller;

import com.dissertation.backend.entity.Candidate;
import com.dissertation.backend.entity.Skill;
import com.dissertation.backend.entity.Summary;
import com.dissertation.backend.model.Login;
import com.dissertation.backend.node.*;
import com.dissertation.backend.projection.CandidateNoPassword;
import com.dissertation.backend.repository.CandidateNodeRepository;
import com.dissertation.backend.repository.CandidateRepository;
import com.dissertation.backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RepositoryRestController

@RequestMapping("/candidate")
@CrossOrigin("http://localhost:4300")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;
    private final ExperienceService experienceService;
    private final EducationService educationService;
    private final CandidateNodeService candidateNodeService;
    private final CandidateRepository candidateRepository;
    private final SummaryService summaryService;
    private final SkillService skillService;
    private final CandidateNodeRepository candidateNodeRepository;

    @GetMapping(value = "/lalak")
    public ResponseEntity<List<CandidateNoPassword>> getCandidateNoPassword() {
        return ResponseEntity.status(HttpStatus.OK).body(candidateRepository.findAllBy());
    }

/*    @PostMapping(value="/register")
    public ResponseEntity<Boolean> register(@RequestBody CandidateNode candidate){
        O isRegistered = candidateService.saveCandidate(candidate);

//        Link categoryLink = links.linkFor(CandidateNode.class).slash("search/save").withRel("candidate");
//        Link selfLink = links.linkFor(CandidateNode.class).slash("search/save").withSelfRel();
        return ResponseEntity.ok(isRegistered);
    }*/

    @PostMapping(value = "/candidate/login")
    public ResponseEntity<Long> login(@RequestBody Login credentials) {
        Long exists = candidateService.checkIfUserExists(credentials.getUsername(), credentials.getPassword());
        return ResponseEntity.ok(exists);
    }


    /**
     * @param id - Long
     * @param candidate - Candidate Obj
     * @return
     */
    @PostMapping(value = "/{id}/save/photo-profile")
    public ResponseEntity<Candidate> setCandidatePhotoProfile(@PathVariable("id") Long id,
            @RequestBody Candidate candidate){
        Candidate c = candidateService.savePhotoProfile(candidate.getProfilePic());
        return ResponseEntity.ok(c);
    }

    /**
     * @param id - Long
     * @return
     */
    @GetMapping(value = "/{id}/get/profile")
    public ResponseEntity<Candidate> getCandidateProfile(@PathVariable("id") Long id){
        Candidate c = candidateService.getProfile(id);
        return ResponseEntity.ok(c);
    }

    /**
     * @param summary
     * @return
     */
    @PostMapping(value = "/{id}/save/summary")
    public ResponseEntity<Summary> setCandidateSummary(@RequestBody Summary summary) {
        Summary sum = summaryService.saveSummary(summary);
        return ResponseEntity.ok(sum);
    }

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}/get/summary")
    public ResponseEntity<Summary> getCandidateSummary(@PathVariable("id") String id) {
        Summary sum = summaryService.getSummary(Long.parseLong(id));
        return ResponseEntity.ok(sum);
    }
    /*----------------------------Experience--------------------------------------------------------------------------*/

    /**
     * @param experienceNode
     * @return
     */
    @PostMapping(value = "/{id}/save/working-experience")
    public ResponseEntity<ExperienceNode> setCandidateWorkExperience(
            @RequestBody ExperienceNode experienceNode,
            @PathVariable("id") Long id) {
        ExperienceNode exp = experienceService.setExperience(experienceNode, id);
        return ResponseEntity.ok(exp);
    }

    /**
     * @param candidateId
     * @return
     */
    @GetMapping(value = "/{id}/get/working-experience/{experienceId}")
    public ResponseEntity<ExperienceNode> getCandidateWorkExperience(
            @PathVariable("id") String candidateId,
            @PathVariable("experienceId") String experienceId) {
        ExperienceNode exp = experienceService.getExperience(experienceId);
        return ResponseEntity.ok(exp);
    }

    /**
     * @param candidateId
     * @return
     */
    @GetMapping(value = "/{id}/get/working-experience")
    public ResponseEntity<List<ExperienceNode>> getCandidateWorkExperienceList(
            @PathVariable("id") Long candidateId) {
        List<ExperienceNode> exp = experienceService.getListExperience(candidateId);
        return ResponseEntity.ok(exp);
    }

    /**
     * @param experienceId
     * @return
     */
    @PatchMapping(value = "/{id}/patch/working-experience/{experienceId}")
    public ResponseEntity<ExperienceNode> patchCandidateWorkExperience(
            @PathVariable("id") Long candidateId,
            @PathVariable("experienceId") String experienceId,
            @RequestBody ExperienceNode experienceNode) {

        ExperienceNode exp =  experienceService.patchExperience(experienceId, experienceNode);
        return ResponseEntity.ok(exp);
    }

    /**
     * @param experienceId
     * @return
     */
    @DeleteMapping(value = "/{id}/delete/working-experience/{experienceId}")
    public ResponseEntity<Boolean> deleteExperienceByExperienceId(@PathVariable("experienceId") String experienceId) {
        Boolean deleteExperienceNode = experienceService.deleteExperience(experienceId);
        return ResponseEntity.ok(!deleteExperienceNode);
    }

    /*----------------------------/Experience-------------------------------------------------------------------------*/

    /*----------------------------Education---------------------------------------------------------------------------*/

    /**
     * @param educationNode
     * @return
     */
    @PostMapping(value = "/{id}/save/education")
    public ResponseEntity<EducationNode> setCandidateEducation(
            @RequestBody EducationNode educationNode,
            @PathVariable("id") Long id) {
        EducationNode ed = educationService.setEducation(educationNode, id);
        return ResponseEntity.ok(ed);
    }

    /**
     * @param candidateId
     * @return
     */
    @GetMapping(value = "/{id}/get/education/{educationId}")
    public ResponseEntity<EducationNode> getCandidateEducation(
            @PathVariable("id") String candidateId,
            @PathVariable("educationId") String educationId) {
        EducationNode ed = educationService.getEducation(educationId);
        return ResponseEntity.ok(ed);
    }

    /**
     * @param candidateId
     * @return
     */
    @GetMapping(value = "/{id}/get/education")
    public ResponseEntity<List<EducationNode>> getCandidateEducationList(@PathVariable("id") Long candidateId) {
        List<EducationNode> ed = educationService.getListEducation(candidateId);
        return ResponseEntity.ok(ed);
    }

    /**
     * @param educationId
     * @return
     */
    @DeleteMapping(value = "/{id}/delete/education/{educationId}")
    public ResponseEntity<Boolean> deleteCandidateEducation(@PathVariable("educationId") String educationId) {
        educationService.deleteEducation(educationId);
        return ResponseEntity.ok(true);
    }

    /*----------------------------/Education--------------------------------------------------------------------------*/

    /*----------------------------Skill-------------------------------------------------------------------------------*/

    /**
     * @param skill - String
     * @return Response Entity
     */
    @GetMapping("/{id}/get/search/skill-list")
    public ResponseEntity<List<Skill>> getSearchSkill(@PathVariable("id") Long candidateId,
                                                      @RequestParam("skill") String skill) {
        List<Skill> skills = skillService.getSkillLike(skill);
        return ResponseEntity.ok(skills);
    }

    /**
     * @param candidateId - Long
     * @return Response Entity
     */
    @GetMapping("/{id}/get/candidate-skill-list")
    public ResponseEntity<Set<CandidateSkillRelationship>> getCandidateSkillList(@PathVariable("id") Long candidateId) {
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









/*    @GetMapping(value = CustomLinks.DTOS)
    public ResponseEntity<?> getDtos() {
        List<CandidateNoPassword> dtos = candidateRepository.getDtos();

        Link listSelfLink = links.linkFor(Candidate.class).slash(CustomLinks.DTOS).withSelfRel();
        List<?> resources = dtos.stream().map(this::toResource).collect(Collectors.toList());

        return ResponseEntity.ok(new CollectionModel<>(resources, listSelfLink));
    }

    private RepresentationModel toResource(CandidateNoPassword projection) {
        CandidateDto dto = new CandidateDto(projection.getId(), projection.getName(), projection.getSurname(), projection.getEmail(), projection.getProfilePic());

        Link categoryLink = links.linkFor(Candidate.class).slash(projection.getId()).withRel("candidate");
        Link selfLink = links.linkFor(Candidate.class).slash(CustomLinks.DTOS).withSelfRel();

        return new EntityModel<CandidateDto>(dto, categoryLink, selfLink);
    }

    */
}
