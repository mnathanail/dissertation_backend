package com.dissertation.backend.controller;

import com.dissertation.backend.node.JobNode;
import com.dissertation.backend.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RepositoryRestController

@RequestMapping("/recruiter")
@CrossOrigin("http://localhost:4300")
@RequiredArgsConstructor
@Validated
public class RecruiterController {

    private final JobService jobService;

    @PostMapping("/{id}/save/job")
    public ResponseEntity<JobNode> saveJob(@PathVariable("id") Long id, @Valid @RequestBody JobNode jobNode){
        JobNode jn = jobService.setJob(id, jobNode);
        return ResponseEntity.ok(jn);
    }

    @PatchMapping("/{recruiterId}/patch/job/{jobId}")
    public ResponseEntity<JobNode> saveJob(
            @PathVariable("recruiterId") Long id,
            @Valid @RequestBody JobNode jobNode,
            @PathVariable("jobId") String jobId ){
        JobNode jn = jobService.updateJob(jobId, jobNode);
        return ResponseEntity.ok(jn);
    }

    @DeleteMapping("/{recruiterId}/delete/job/{jobId}")
    public ResponseEntity<Boolean> deleteJob(@PathVariable("recruiterId") Long recruiterId, @PathVariable String jobId){
        Boolean jn = jobService.deleteJob(recruiterId, jobId);
        return ResponseEntity.ok(jn);
    }

}
