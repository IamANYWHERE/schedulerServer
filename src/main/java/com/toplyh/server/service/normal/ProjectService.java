package com.toplyh.server.service.normal;

import com.toplyh.server.model.entity.project.Project;
import com.toplyh.server.model.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 我 on 2017/11/28.
 */
@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository=projectRepository;
    }

    public Project add(Project project){
        projectRepository.save(project);
        return projectRepository.findOne(project.getId());
    }

    public Project findById(Integer id){
        return projectRepository.findOne(id);
    }

    public List<Project> findByUserName(String name){
        return projectRepository.findByUserName(name);
    }

    public Boolean exists(Integer id){
        return projectRepository.exists(id);
    }
}
