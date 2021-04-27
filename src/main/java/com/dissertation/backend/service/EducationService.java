package com.dissertation.backend.service;

import com.dissertation.backend.exception.custom.education_exception.EducationNotFoundException;
import com.dissertation.backend.node.CandidateNode;
import com.dissertation.backend.node.EducationNode;
import com.dissertation.backend.node.RecruiterNode;
import com.dissertation.backend.repository.CandidateNodeRepository;
import com.dissertation.backend.repository.CandidateRepository;
import com.dissertation.backend.repository.EducationNodeRepository;
import com.dissertation.backend.repository.RecruiterNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final CandidateNodeRepository candidateNodeRepository;

    private final EducationNodeRepository educationNodeRepository;

    private final CandidateRepository candidateRepository;

    private final RecruiterNodeRepository recruiterNodeRepository;


    /**
     * @param education   - EducationNode Obj
     * @param candidateId - Candidate Id
     * @return EducationNode | notfound
     */
    public EducationNode setEducation(EducationNode education, Long candidateId) {
        boolean isRecruiter = this.isRecruiter(candidateId);
        if(isRecruiter){
            Optional<RecruiterNode> rq = recruiterNodeRepository.findByEntityId(candidateId);
            if (rq.isPresent()) {
                education.setEducationId(UUID.randomUUID().toString());
                EducationNode edu = educationNodeRepository.save(education);
                String educationId = edu.getEducationId();
                recruiterNodeRepository.createRelationRecruiterEducation(candidateId, educationId);

                return edu;
            } else {
                throw new NotFoundException();
            }
        }
        else {
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
    }

    /**
     * @param educationId - EducationNode Id
     * @return EducationNode | null
     */
    public EducationNode getEducation(String educationId) {
        Optional<EducationNode> edu = educationNodeRepository.findByEducationId(educationId);

        return edu.orElseThrow(()-> new EducationNotFoundException("Education not found " + educationId));
    }

    /**
     * @param candidateId - Candidate Id
     * @return educationNode List | empty list
     */
    public Set<EducationNode> getListEducation(Long candidateId) {
        boolean isRecruiter = this.isRecruiter(candidateId);
        if(isRecruiter){
            Optional<RecruiterNode> cn = recruiterNodeRepository.findByEntityId(candidateId);
            return cn.map(RecruiterNode::getEducationNodes).orElse(new HashSet<>());
        }
        else{
            Optional<CandidateNode> cn = candidateNodeRepository.findCandidateNodeByEntityId(candidateId);
            return cn.map(CandidateNode::getEducationNodes).orElse(new HashSet<>());
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
            updateExperience(educationParam, education);
            //BeanUtils.copyProperties(educationParam, education, getNullPropertyNames(educationParam));
            return educationNodeRepository.save(education);
        } else {
            throw new EducationNotFoundException("Education not found " + educationId);
        }
    }

    private void updateExperience(EducationNode educationNode, EducationNode education) {
        if (educationNode.getTitle() != null) {
            education.setTitle(educationNode.getTitle());
        }
        if (educationNode.getDegree() != null) {
            education.setDegree(educationNode.getDegree());
        }
        if (educationNode.getSchool() != null) {
            education.setSchool(educationNode.getSchool());
        }
        if(educationNode.getPeriod().getStartMonth() != null){
            education.getPeriod().setStartMonth(educationNode.getPeriod().getStartMonth());
        }
        if(educationNode.getPeriod().getStartYear() != null){
            education.getPeriod().setStartYear(educationNode.getPeriod().getStartYear());
        }
        if(educationNode.getPeriod().getEndMonth() != null){
            education.getPeriod().setEndMonth(educationNode.getPeriod().getEndMonth());
        }
        if(educationNode.getPeriod().getEndYear() != null){
            education.getPeriod().setEndYear(educationNode.getPeriod().getEndYear());
        }
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

    private boolean isRecruiter(Long id){
        Utils util = new Utils(candidateRepository);
        return util.isRecruiter(id);
    }
}
