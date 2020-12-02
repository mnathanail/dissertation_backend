package com.dissertation.backend.repository;

import com.dissertation.backend.entity.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "summary", path = "summary")
public interface SummaryRepository extends JpaRepository<Summary, Long>, JpaSpecificationExecutor<Summary> {

}