package com.mokylin.gm.entity;

/**
 * 消息类型枚举
 * Created by Administrator on 2014/6/20.
 */
public enum MessageType {

    INFO("info"),SUCCESS("success"),WARNING("warning"),ERROR("error"),LOADING("loading");

    private MessageType(String type){
        this.type = type;
    }

    private String type ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
