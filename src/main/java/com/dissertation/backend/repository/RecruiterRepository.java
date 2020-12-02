package com.dissertation.backend.repository;

import com.dissertation.backend.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "RecruiterNode", path = "recruiter" )
public interface RecruiterRepository extends JpaRepository<Recruiter, Long>, JpaSpecificationExecutor<Recruiter> {

}