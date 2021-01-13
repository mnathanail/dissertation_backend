package com.dissertation.backend.controller;

import com.dissertation.backend.entity.Skill;
import com.dissertation.backend.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RepositoryRestController
@RequestMapping("/skill")
@RequiredArgsConstructor
@Validated
public class SkillController {

    private final SkillService skillService;
    /**
     * @param skill - String
     * @return ResponseEntity<List<Skill>>
     */
    @GetMapping("/get/search/skill-list")
    public ResponseEntity<List<Skill>> getSearchSkill(@RequestParam("skill") String skill) {
        List<Skill> skills = skillService.getSkillLike(skill);
        return ResponseEntity.ok(skills);
    }
}
