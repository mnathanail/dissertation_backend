package com.dissertation.backend.service;

import com.dissertation.backend.exception.custom.candidate_exception.CandidateNotFoundException;
import com.dissertation.backend.exception.custom.experience_exception.ExperienceNotFoundException;
import com.dissertation.backend.exception.custom.recruiter_exception.RecruiterNotFoundException;
import com.dissertation.backend.node.CandidateNode;
import com.dissertation.backend.node.ExperienceNode;
import com.dissertation.backend.node.RecruiterNode;
import com.dissertation.backend.repository.CandidateNodeRepository;
import com.dissertation.backend.repository.CandidateRepository;
import com.dissertation.backend.repository.ExperienceNodeRepository;
import com.dissertation.backend.repository.RecruiterNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final CandidateNodeRepository candidateNodeRepository;

    private final CandidateRepository candidateRepository;

    private final RecruiterNodeRepository recruiterNodeRepository;

    private final ExperienceNodeRepository experienceNodeRepository;

    /**
     * @param experience - Experience Obj
     * @param candidateId - Candidate Id
     * @return experienceNode | notfound
     */
    public ExperienceNode setExperience(ExperienceNode experience, Long candidateId) {
        boolean isRecruiter = this.isRecruiter(candidateId);
        if(isRecruiter){
            Optional<RecruiterNode> rn = recruiterNodeRepository.findByEntityId(candidateId);
            if (rn.isPresent()) {
                experience.setExperienceId(UUID.randomUUID().toString());
                ExperienceNode exp = experienceNodeRepository.save(experience);
                String experienceId = exp.getExperienceId();
                recruiterNodeRepository.createRelationRecruiterExperience(candidateId, experienceId);

                return exp;
            } else {
                throw new RecruiterNotFoundException("Recruiter not found!");
            }
        }
        else{
            Optional<CandidateNode> cn = candidateNodeRepository.findCandidateNodeByEntityId(candidateId);
            if (cn.isPresent()) {
                experience.setExperienceId(UUID.randomUUID().toString());
                ExperienceNode exp = experienceNodeRepository.save(experience);
                String experienceId = exp.getExperienceId();
                candidateNodeRepository.createRelationCandidateExperience(candidateId, experienceId);

                return exp;
            } else {
                throw new CandidateNotFoundException("Candidate not found!");
            }
        }

    }

    /**
     * @param experienceId - Experience Id
     * @return experienceNode | null
     */
    public ExperienceNode getExperience(String experienceId) {
        Optional<ExperienceNode> epx = experienceNodeRepository.findByExperienceId(experienceId);
        return epx.orElse(null);
    }

    /**
     * @param candidateId - Candidate Id
     * @return experienceNode List | empty list
     */
    public List<ExperienceNode> getListExperience(Long candidateId) {
        boolean isRecruiter = this.isRecruiter(candidateId);
        if(isRecruiter){
            return experienceNodeRepository.findRecruiterExperience(candidateId);
        }
        else{
            return experienceNodeRepository.findCandidateExperience(candidateId);
        }

    }

    /**
     * @param experienceId - Experience Id
     */
    public boolean deleteExperience(String experienceId) {
        ExperienceNode experienceNodeDelete =
                experienceNodeRepository.deleteExperienceNodeByExperienceIdCustom(experienceId);

        Optional<ExperienceNode> experienceNode = experienceNodeRepository.findByExperienceId(experienceId);

        return experienceNode.isPresent();
    }

    /**
     * @param experienceId - Id
     * @param experienceParam - experience parameter
     * @return experienceNode
     */
    public ExperienceNode patchExperience(String experienceId, ExperienceNode experienceParam) {
        Optional<ExperienceNode> exp = experienceNodeRepository.findByExperienceId(experienceId);
        if(exp.isPresent()){
            ExperienceNode experience = exp.get();
            updateExperience(experienceParam, experience);
            return experienceNodeRepository.save(experience);
        }
        else {
            throw new ExperienceNotFoundException("Experience not found " + experienceId);
        }
    }

    private void updateExperience(ExperienceNode experienceNode, ExperienceNode experience) {
        if(experienceNode.getJobTitle()!= null){
            experience.setJobTitle(experienceNode.getJobTitle());
        }
        if(experienceNode.getCompanyName()!= null){
            experience.setCompanyName(experienceNode.getCompanyName());
        }
        if(experienceNode.getIndustry()!= null){
            experience.setIndustry(experienceNode.getIndustry());
        }
        if(experienceNode.getDescription()!= null){
            experience.setDescription(experienceNode.getDescription());
        }
        if(experienceNode.getIsCurrent()!= null) {
            experience.setIsCurrent(experienceNode.getIsCurrent());
        }
        if(experienceNode.getPeriod().getStartMonth() != null){
            experience.getPeriod().setStartMonth(experienceNode.getPeriod().getStartMonth());
        }
        if(experienceNode.getPeriod().getStartYear() != null){
            experience.getPeriod().setStartYear(experienceNode.getPeriod().getStartYear());
        }
        if(experienceNode.getPeriod().getEndMonth() != null){
            experience.getPeriod().setEndMonth(experienceNode.getPeriod().getEndMonth());
        }
        if(experienceNode.getPeriod().getEndYear() != null){
            experience.getPeriod().setEndYear(experienceNode.getPeriod().getEndYear());
        }
    }

    public  String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if(pd.getName().equals("period")){
                System.out.println(123);
            }
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
