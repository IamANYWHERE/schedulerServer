package com.toplyh.server.model.json.data;

import com.toplyh.server.model.entity.project.Project;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

/**
 * Created by 我 on 2017/12/25.
 */
public class SimpProject {
    private Integer id;

    private String projectName;

    private Date ddl;

    private Integer progress;

    private String username;

    public SimpProject(){

    }

    public SimpProject(Project project){
        this.id=project.getId();
        this.projectName=project.getProjectName();
        this.ddl=project.getDdl();
        this.progress=project.getProgress();
        username=project.getUser().getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getDdl() {
        return ddl;
    }

    public void setDdl(Date ddl) {
        this.ddl = ddl;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
