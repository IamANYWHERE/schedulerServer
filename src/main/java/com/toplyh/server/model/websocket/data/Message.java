package com.toplyh.server.model.websocket.data;

/**
 * Created by 我 on 2017/12/20.
 */
public class Message<T> {

    private Integer index;

    private T data;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
