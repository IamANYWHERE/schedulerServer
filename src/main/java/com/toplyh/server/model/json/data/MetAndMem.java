package com.toplyh.server.model.json.data;

import java.util.Date;
import java.util.List;

/**
 * Created by 我 on 2017/12/31.
 */
public class MetAndMem {

    private Integer meetingId;

    private String name;

    private Date date;

    private Integer projectId;

    private List<String> members;

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
