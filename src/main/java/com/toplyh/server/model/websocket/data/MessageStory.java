package com.toplyh.server.model.websocket.data;

/**
 * Created by 我 on 2017/12/20.
 */
public class MessageStory {
    //描述人
    private String origin;
    //内容
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
