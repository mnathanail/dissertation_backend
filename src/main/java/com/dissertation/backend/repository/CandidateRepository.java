package com.dissertation.backend.repository;

import com.dissertation.backend.entity.Candidate;
import com.dissertation.backend.projection.CandidateDto;
import com.dissertation.backend.projection.CandidateNoPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = { "http://localhost", "http://localhost:4300" })
@RepositoryRestResource(collectionResourceRel = "candidate", path = "candidate", excerptProjection = CandidateNoPassword.class)
public interface CandidateRepository extends JpaRepository<Candidate, Long>, JpaSpecificationExecutor<Candidate> {

   // boolean existsByEmailAndPassword(String email, String password);

    Optional<Candidate> findCandidateByEmailAndPassword(String email, String password);

    Candidate findCandidateNameSurnameEmailById(Long id);

    Candidate save(Candidate candidate);
    //List<NodeCandidate> findAllByName(String id);

    /*@Query("SELECT t.name, t.surname, t.profilePic FROM NodeCandidate t where t.name = :name")
    List<NodeCandidate> findAllByName(@Param("name") String name);*/

    @RestResource(path = "candidateDto")
    //@Query(value = "SELECT new com.dissertation.backend.projection.CandidateDto(c.id, c.name, c.surname, c.email, c.profilePic) FROM NodeCandidate c WHERE c.name = :name")
    List<CandidateDto> findCandidateByName(String name);

   /* @Query("SELECT  a.id as id, a.name as name FROM NodeCandidate a")*/
    List<CandidateNoPassword> findAllBy();

    Candidate findBySurname(String surname);


    @Query("SELECT t.id as id, t.name as name, t.surname as surname, t.email as email, t.profilePic as profile_pic FROM Candidate t")
    List<CandidateNoPassword> getDtos();


}