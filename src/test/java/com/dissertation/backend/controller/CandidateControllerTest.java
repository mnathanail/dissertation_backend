package com.dissertation.backend.controller;

import com.dissertation.backend.entity.Candidate;
import com.dissertation.backend.service.CandidateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest
@Import(ValidationAutoConfiguration.class)
class CandidateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidateService candidateService;

    @Test
    void register() {
        Candidate candidate = new Candidate();
        candidate.setCandidateId("300");
        given(candidateService.register(any(Candidate.class))).willReturn(candidate);
    }
}