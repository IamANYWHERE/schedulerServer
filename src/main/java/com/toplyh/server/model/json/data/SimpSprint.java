package com.toplyh.server.model.json.data;

import com.toplyh.server.model.entity.project.sprint.Sprint;

import java.util.Date;

/**
 * Created by 我 on 2017/12/30.
 */
public class SimpSprint {

    private Integer id;
    private String name;
    private String content;
    private Date ddl;
    private Sprint.SprintStatus status;
    private Integer workTime;
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDdl() {
        return ddl;
    }

    public void setDdl(Date ddl) {
        this.ddl = ddl;
    }

    public Sprint.SprintStatus getStatus() {
        return status;
    }

    public void setStatus(Sprint.SprintStatus status) {
        this.status = status;
    }

    public Integer getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
