package com.toplyh.server.model.json.data;

/**
 * Created by 我 on 2017/11/30.
 */
public class ShowMember {

    private String name;

    private String nickName;

    private Integer contribution;

    public Integer getContribution() {
        return contribution;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
