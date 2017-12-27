package com.toplyh.server.service.normal;

import com.toplyh.server.model.entity.skill.Skill;
import com.toplyh.server.model.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 我 on 2017/11/25.
 */
@Service
public class SkillService {

    private SkillRepository skillRepository;

    @Autowired
    public SkillService(SkillRepository skillRepository){
        this.skillRepository=skillRepository;
    }

    public Skill add(Skill skill){
        skillRepository.save(skill);
        return skillRepository.findOne(skill.getId());
    }

    public Skill findById(int id){
        return skillRepository.findOne(id);
    }

    public List<Skill> findByUserName(String name){
        return skillRepository.findByUserName(name);
    }
}
