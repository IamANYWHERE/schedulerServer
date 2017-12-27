package com.toplyh.server.model.repository;

import com.toplyh.server.model.entity.skill.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by 我 on 2017/11/25.
 */
public interface SkillRepository extends CrudRepository<Skill,Integer>{
    List<Skill> findByUserName(String name);
}
