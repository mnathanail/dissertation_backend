package com.dissertation.backend.repository;

import com.dissertation.backend.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;
@CrossOrigin(origins = { "http://localhost", "http://localhost:4300" })
//, excerptProjection = CandidateNoPassword.class
@RepositoryRestResource(collectionResourceRel = "candidate", path = "candidate")
public interface CandidateRepository extends JpaRepository<Candidate, Long>, JpaSpecificationExecutor<Candidate> {

   // boolean existsByEmailAndPassword(String email, String password);

    Optional<Candidate> findCandidateByEmailAndPassword(String email, String password);

    Optional<Candidate> findCandidateByEmail(String email);

    Candidate findCandidateNameSurnameEmailById(Long id);

    Candidate findBySurname(String surname);


}