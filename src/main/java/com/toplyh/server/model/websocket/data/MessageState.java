package com.toplyh.server.model.websocket.data;

import com.toplyh.server.model.entity.project.sprint.Sprint;
import com.toplyh.server.service.normal.SprintService;

/**
 * Created by 我 on 2017/12/20.
 */
public class MessageState {

    private Integer target;

    private String status;

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
