package com.mokylin.gm.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/6/20.
 */
public class ResultMsg implements Serializable{

    private static final long serialVersionUID = 1L;

    // 1 成功， 0 失败
    private int status;

    private MessageType type;

    private String msg;

    public ResultMsg(int status, MessageType type, String msg) {
        this.status = status;
        this.type = type;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
