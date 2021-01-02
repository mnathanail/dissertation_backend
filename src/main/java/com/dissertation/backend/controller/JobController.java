package com.dissertation.backend.controller;

import com.dissertation.backend.node.JobNode;
import com.dissertation.backend.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RepositoryRestController

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

    @GetMapping("/candidate/search/job/keywords")
    ResponseEntity<List<JobNode>> getCandidateSearchJobByKeywords(@RequestParam(value = "keywords") List<String> keywords){
        List<JobNode> jobNodeList = jobService.candidateSearchJobByKeywords(keywords);
        return ResponseEntity.ok(jobNodeList);
    }
}
