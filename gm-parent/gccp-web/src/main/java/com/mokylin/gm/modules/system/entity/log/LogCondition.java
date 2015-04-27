package com.mokylin.gm.modules.system.entity.log;

/**
 * 日志查询条件
 * Created by Administrator on 2014/6/30.
 */
public class LogCondition {
    private String userName;

    private String type;

    private String beginTime;

    private String endTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
