package com.toplyh.server.model.repository;

import com.toplyh.server.model.entity.project.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by 我 on 2017/11/28.
 */
public interface ProjectRepository extends CrudRepository<Project,Integer> {
    List<Project> findByUserName(String name);
}
