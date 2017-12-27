package com.toplyh.server.model.entity.project.story;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.toplyh.server.model.entity.project.Project;

import javax.persistence.*;

/**
 * Created by 我 on 2017/11/28.
 */
@Entity(name = "Story")
@Table(name = "story")
public class Story {

    public static enum StoryStatus {
        YES,NO
    }

    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    @Enumerated(EnumType.ORDINAL)
    private StoryStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;



    public Story(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StoryStatus getStatus() {
        return status;
    }

    public void setStatus(StoryStatus status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
