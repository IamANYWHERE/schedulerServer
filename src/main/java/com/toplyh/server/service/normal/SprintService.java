package com.toplyh.server.service.normal;

import com.toplyh.server.model.entity.project.sprint.Sprint;
import com.toplyh.server.model.entity.skill.Skill;
import com.toplyh.server.model.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

/**
 * Created by 我 on 2017/12/3.
 */
@Service
public class SprintService {

    private SprintRepository sprintRepository;

    @Autowired
    public SprintService(SprintRepository sprintRepository){
        this.sprintRepository=sprintRepository;
    }

    public Sprint add(Sprint sprint){
        sprintRepository.save(sprint);
        return sprintRepository.findOne(sprint.getId());
    }

    public Sprint findById(Integer id){
        return sprintRepository.findOne(id);
    }

    public List<Sprint> findByProjectId(Integer id){
        return sprintRepository.findByProjectId(id);
    }

    public List<Sprint> findByProjectIdAndMemberId(Integer projectId,Integer memberId){
        return sprintRepository.findByProjectIdAndMemberId(projectId,memberId);
    }

    public List<Sprint> findByProjectIdAndStatus(Integer projectId, Sprint.SprintStatus status){
        return sprintRepository.findByProjectIdAndStatus(projectId,status);
    }

    public List<Sprint> findByProjectIdAndMemberIdAndStatus(Integer projectId, Integer memberId, Sprint.SprintStatus status){
        return sprintRepository.findByProjectIdAndMemberIdAndStatus(projectId,memberId,status);
    }

    public boolean existsByStatus(Sprint.SprintStatus status){
        return sprintRepository.existsByStatus(status);
    }

    public boolean exists(Integer sprintId){
        return sprintRepository.exists(sprintId);
    }
}
