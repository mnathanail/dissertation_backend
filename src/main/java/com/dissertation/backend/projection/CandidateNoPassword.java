package com.dissertation.backend.projection;


import com.dissertation.backend.entity.Candidate;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "candidateNoPassword", types = {Candidate.class})
public interface CandidateNoPassword {
    Long getId();
    String getName();
    String getSurname();
    String getEmail();
    byte[] getProfilePic();
}
