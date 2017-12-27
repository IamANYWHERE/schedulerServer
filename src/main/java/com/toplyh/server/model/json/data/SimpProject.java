package com.toplyh.server.model.json.data;

import com.toplyh.server.model.entity.project.Project;

import java.util.Date;

/**
 * Created by 我 on 2017/12/25.
 */
public class SimpProject {
    private Integer id;

    private String projectName;

    private Date ddl;

    private Float progress;

    public SimpProject(Project project){
        this.id=project.getId();
        this.projectName=project.getProjectName();
        this.ddl=project.getDdl();
        this.progress=project.getProgress();
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

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }
}
