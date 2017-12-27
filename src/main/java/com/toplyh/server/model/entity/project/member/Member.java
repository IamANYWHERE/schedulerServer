package com.toplyh.server.model.entity.project.member;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toplyh.server.model.entity.User;
import com.toplyh.server.model.entity.project.Project;
import com.toplyh.server.model.entity.project.meeting.Meeting;
import com.toplyh.server.model.entity.project.sprint.Sprint;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 我 on 2017/11/29.
 */
@Entity(name = "Member")
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    private Integer contribution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;


    @ManyToMany
    @JoinTable(
            joinColumns = {@JoinColumn(name = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "meeting_id")}
    )
    @JsonBackReference
    private Set<Meeting> meetings=new HashSet<>();

    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<Sprint> sprints=new HashSet<>();


    public Member(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getContribution() {
        return contribution;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }

    public Set<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(Set<Sprint> sprints) {
        this.sprints = sprints;
    }
}
