package com.dissertation.backend.service;

import com.dissertation.backend.node.CandidateNode;
import com.dissertation.backend.node.EducationNode;
import com.dissertation.backend.repository.CandidateNodeRepository;
import com.dissertation.backend.repository.EducationNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final CandidateNodeRepository candidateNodeRepository;

    private final EducationNodeRepository educationNodeRepository;

    /**
     * @param education   - EducationNode Obj
     * @param candidateId - Candidate Id
     * @return EducationNode | notfound
     */
    public EducationNode setEducation(EducationNode education, Long candidateId) {
        Optional<CandidateNode> cn = candidateNodeRepository.findCandidateNodeByEntityId(candidateId);
        if (cn.isPresent()) {
            education.setEducationId(UUID.randomUUID().toString());
            EducationNode edu = educationNodeRepository.save(education);
            String educationId = edu.getEducationId();
            candidateNodeRepository.createRelationCandidateEducation(candidateId, educationId);

            return edu;
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * @param educationId - EducationNode Id
     * @return EducationNode | null
     */
    public EducationNode getEducation(String educationId) {
        Optional<EducationNode> edu = educationNodeRepository.findByEducationId(educationId);
        return edu.orElseThrow(ArithmeticException::new);
    }

    /**
     * @param candidateId - Candidate Id
     * @return educationNode List | empty list
     */
    public Set<EducationNode> getListEducation(Long candidateId) {
        Optional<CandidateNode> cn = candidateNodeRepository.findCandidateNodeByEntityId(candidateId);
        if (cn.isPresent()) {
            return cn.get().getEducationNodes();
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * @param educationId - Education Id
     */
    public boolean deleteEducation(String educationId) {
        EducationNode experienceNodeDelete =
                educationNodeRepository.deleteExperienceNodeByEducationIdCustom(educationId);

        Optional<EducationNode> educationNode = educationNodeRepository.findByEducationId(educationId);

        return educationNode.isPresent();
    }

    /**
     * @param educationId    - Id
     * @param educationParam - education parameter
     * @return educationNode
     */
    public EducationNode patchEducation(String educationId, EducationNode educationParam) {
        Optional<EducationNode> edu = educationNodeRepository.findByEducationId(educationId);
        if (edu.isPresent()) {
            EducationNode education = edu.get();

            // experience = updateExperience(experienceParam, experience);

            BeanUtils.copyProperties(educationParam, education, getNullPropertyNames(educationParam));

            System.out.println(education);
            EducationNode updatedEducation = educationNodeRepository.save(education);
            return updatedEducation;
        } else {
            return edu.orElseThrow(ArithmeticException::new);
        }
    }

    private EducationNode updateExperience(EducationNode educationNode, EducationNode education) {
        if (educationNode.getTitle() != null) {
            education.setTitle(educationNode.getTitle());
        }
        if (educationNode.getDegree() != null) {
            education.setDegree(educationNode.getDegree());
        }
        if (educationNode.getSchool() != null) {
            education.setSchool(educationNode.getSchool());
        }
        return education;
    }

    public String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
