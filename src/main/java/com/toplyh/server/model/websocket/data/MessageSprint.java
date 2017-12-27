package com.toplyh.server.model.websocket.data;

/**
 * Created by 我 on 2017/12/20.
 */
public class MessageSprint {

    //发起人
    private String origin;
    //描述
    private String description;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
