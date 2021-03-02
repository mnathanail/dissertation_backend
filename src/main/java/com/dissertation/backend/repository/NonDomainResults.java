package com.dissertation.backend.repository;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface NonDomainResults {

    class Result {
        public final String candidateName;

        public final String candidateEntityId;
        public final String percent;

        public Result(String candidateName, String candidateEntityId, String percent) {
            this.candidateName = candidateName;
            this.candidateEntityId = candidateEntityId;
            this.percent = percent;

        }
    }

    @Transactional(readOnly = true)
    Collection<Result> findRecommendedCandidatesForJob(String jobId);

}