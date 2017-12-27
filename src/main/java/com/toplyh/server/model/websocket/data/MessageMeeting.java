package com.toplyh.server.model.websocket.data;

import java.util.Date;
import java.util.List;

/**
 * Created by 我 on 2017/12/20.
 */
public class MessageMeeting {
    //会议发起人
    private String origin;
    //会议参与者
    private List<String> targets;
    //会议描述
    private String description;
    //会议时间
    private Date time;

    private Integer projectId;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
