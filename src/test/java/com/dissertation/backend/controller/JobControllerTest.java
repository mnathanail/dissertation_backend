package com.dissertation.backend.controller;

import com.dissertation.backend.service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JobControllerTest {

    private MockMvc mvc;

    private MockMvc mvcContext;

    @InjectMocks
    private JobController jobController;

    @Mock
    private JobService jobService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(jobController)
                .build();

        mvcContext = MockMvcBuilders
                .webAppContextSetup(context)
                .dispatchOptions(true)
                .build();
    }
    @Test
    void getJobUnit() throws Exception {
        MockHttpServletResponse response = mvcContext.perform(
                get("/job/get/job/{jobId}", "ee1bab27-0558-4287-8452-4b6c437c7645")
        ).andReturn().getResponse();
        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void getJobIntegration() throws Exception {
        String jsonContent = "{ " +
                "\"id\" : 3, " +
                "\"jobId\" : \"ee1bab27-0558-4287-8452-4b6c437c7645\", " +
                "\"jobTitle\" : \"Java Developer\", " +
                "\"description\" : \"<p>Creates user information solutions by developing, implementing, " +
                                        "and maintaining Java based components and interfaces.!</p>\", " +
                "\"requiredSkills\" : [ " +
                "{ \"skillNode\" : " +
                "{ \"name\" : \"Core Java\", \"entityId\" : 7225 }, " +
                "\"relUuid\" : \"5711ab1f-6df1-4448-93d6-7fd6ff1fd836\", \"yearsOfExperience\" : 1 }, " +
                "{ \"skillNode\" : { \"name\" : \"Java\", \"entityId\" : 17477 }, " +
                "\"relUuid\" : \"5a782ab7-5d3d-43bd-8153-565469d9ee9a\", \"yearsOfExperience\" : 1 }, " +
                "{ \"skillNode\" : { \"name\" : \"Java EE\", \"entityId\" : 0 }, " +
                "\"relUuid\" : \"d4d81fef-ee21-4221-99df-47e419f7c0ae\", \"yearsOfExperience\" : 1 }, " +
                "{ \"skillNode\" : { \"name\" : \"Java API\", \"entityId\" : 17478 }, \"" +
                "relUuid\" : \"703c5b53-550e-4d40-b831-9545f4c28b15\", \"yearsOfExperience\" : 1 } ] }";
        ResultActions response = mvcContext.perform(
                get("/job/get/job/{jobId}", "ee1bab27-0558-4287-8452-4b6c437c7645")
        ).andExpect(content().json(jsonContent));
    }
}