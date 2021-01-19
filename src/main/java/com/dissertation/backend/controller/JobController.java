package com.dissertation.backend.controller;

import com.dissertation.backend.node.JobNode;
import com.dissertation.backend.node.RecruiterNode;
import com.dissertation.backend.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RepositoryRestController

@Validated
@RequestMapping("/job")
@CrossOrigin("http://localhost:4300")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("/get/job/{jobId}")
    ResponseEntity<JobNode> getJob(@PathVariable("jobId") String jobId){
        JobNode jn = jobService.getJobByJobId(jobId);
        return ResponseEntity.ok(jn);
    }

    @PostMapping("/apply/candidate/{candidateId}/job/{jobId}")
    ResponseEntity<Boolean> applyForJob(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("jobId") String jobId){
        Boolean jn = jobService.candidateApplyForJob(candidateId,jobId);
        return ResponseEntity.ok(jn);
    }

    @DeleteMapping("/delete/apply/candidate/{candidateId}/job/{jobId}")
    ResponseEntity<Boolean> deleteApplyForJob(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("jobId") String jobId){
        Boolean jn = jobService.candidateDeleteApplyForJob(candidateId,jobId);
        return ResponseEntity.ok(jn);
    }

    @GetMapping("/already/apply/candidate/{candidateId}/job/{jobId}")
    ResponseEntity<Boolean> alreadyApplyForJob(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("jobId") String jobId){
        Boolean jn = jobService.candidateHasAlreadyAppliedForJob(candidateId,jobId);
        return ResponseEntity.ok(jn);
    }

    @GetMapping("/candidate/search/job/keywords")
    ResponseEntity<Page<JobNode>> getCandidateSearchJobByKeywords(
            @RequestParam(value = "keywords") List<String> keywords,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size){

        Page<JobNode> jobNodeList = jobService.candidateSearchJobByKeywords(keywords, page, size);

        final long count = jobService.countCandidateSearchJobByKeywords(keywords);
        //Page a= new PageImpl(jobNodeList, PageRequest.of(page, size), count);
        return ResponseEntity.ok(jobNodeList);
    }

    @GetMapping("/get/recruiter/job/{jobId}")
    ResponseEntity<String> getRecruiterByJobId(@PathVariable("jobId") String jobId){
        RecruiterNode recruiterNode = jobService.getRecruiterByJobId(jobId);
        return ResponseEntity.ok(recruiterNode.getRecruiterId());
    }
}
