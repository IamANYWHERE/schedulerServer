package com.toplyh.server.model.entity.project.meeting;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.toplyh.server.model.entity.project.Project;
import com.toplyh.server.model.entity.project.member.Member;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 我 on 2017/11/29.
 */
@Entity(name = "Meeting")
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue
    private Integer id;

    private Date date;

    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;

    @ManyToMany
    private Set<Member> members=new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }
}
