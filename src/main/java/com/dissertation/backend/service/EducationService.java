package com.dissertation.backend.service;

import com.dissertation.backend.node.CandidateNode;
import com.dissertation.backend.node.EducationNode;
import com.dissertation.backend.repository.CandidateNodeRepository;
import com.dissertation.backend.repository.EducationNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final CandidateNodeRepository candidateNodeRepository;

    private final EducationNodeRepository educationNodeRepository;

    /**
     * @param education - Education Obj
     * @param candidateId - Candidate Id
     * @return educationNode | notfound
     */
    public EducationNode setEducation(EducationNode education, Long candidateId) {
        Optional<CandidateNode> cn = candidateNodeRepository.findById(candidateId);
        if (cn.isPresent()) {
            EducationNode ed = educationNodeRepository.save(education);
            candidateNodeRepository.createRelationCandidateEducation(candidateId, ed.getId());
            return ed;
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * @param educationId - Education Id
     * @return EducationNode | null
     */
    public EducationNode getEducation(Long educationId) {
        Optional<EducationNode> ed = educationNodeRepository.findById(educationId);
        return ed.orElseThrow(ArithmeticException::new);
    }

    /**
     * @param candidateId - Candidate Id
     * @return educationNode List | empty list
     */
    public List<EducationNode> getListEducation(Long candidateId) {
        List<EducationNode> educationNodeList =
                educationNodeRepository.findCandidateEducation(candidateId);
        return educationNodeList;
    }

    /**
     * @param educationId - Education Id
     */
    public void deleteEducation(Long educationId) {
        educationNodeRepository.deleteById(educationId);
    }
}
