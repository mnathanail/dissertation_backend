package com.dissertation.backend.service;

import com.dissertation.backend.node.CandidateNode;
import com.dissertation.backend.node.ExperienceNode;
import com.dissertation.backend.repository.CandidateNodeRepository;
import com.dissertation.backend.repository.ExperienceNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final CandidateNodeRepository candidateNodeRepository;

    private final ExperienceNodeRepository experienceNodeRepository;

    /**
     * @param experience - Experience Obj
     * @param candidateId - Candidate Id
     * @return experienceNode | notfound
     */
    public ExperienceNode setExperience(ExperienceNode experience, Long candidateId) {
        Optional<CandidateNode> cn = candidateNodeRepository.findCandidateNodeByEntityId(candidateId);
        if (cn.isPresent()) {
            experience.setExperienceId(UUID.randomUUID().toString());
            ExperienceNode exp = experienceNodeRepository.save(experience);
            String experienceId = exp.getExperienceId();
            candidateNodeRepository.createRelationCandidateExperience(candidateId, experienceId);

            return exp;
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * @param experienceId - Experience Id
     * @return experienceNode | null
     */
    public ExperienceNode getExperience(String experienceId) {
        Optional<ExperienceNode> epx = experienceNodeRepository.findByExperienceId(experienceId);
        return epx.orElseThrow(ArithmeticException::new);
    }

    /**
     * @param candidateId - Candidate Id
     * @return experienceNode List | empty list
     */
    public List<ExperienceNode> getListExperience(Long candidateId) {
        List<ExperienceNode> experienceNodeList =
                experienceNodeRepository.findCandidateExperience(candidateId);
        return experienceNodeList;
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

           // experience = updateExperience(experienceParam, experience);

            BeanUtils.copyProperties(experienceParam, experience, getNullPropertyNames(experienceParam));

            System.out.println(experience);
            ExperienceNode updatedExperience = experienceNodeRepository.save(experience);
            return  updatedExperience;
        }
        else {
            return exp.orElseThrow(ArithmeticException::new);
        }
    }

    private ExperienceNode updateExperience(ExperienceNode experienceNode, ExperienceNode experience) {
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
        if(experienceNode.getIsCurrent()!= null){
            experience.setIsCurrent(experienceNode.getIsCurrent());
        }
        return experience;
    }

    public  String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
