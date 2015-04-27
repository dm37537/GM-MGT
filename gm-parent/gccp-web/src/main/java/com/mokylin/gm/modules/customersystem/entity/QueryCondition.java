package com.mokylin.gm.modules.customersystem.entity;

import com.mokylin.gm.entity.IEntity;
import com.mokylin.gm.utils.DateUtils;

import java.util.Date;

/**
 * 查询条件
 * Created by Administrator on 2014/7/17.
 */
public class QueryCondition extends IEntity{

    private String beginTime;

    private String endTime;

    public QueryCondition() {
        super();
        checkBeginEndTime();
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

    // 检查查询时间设置
    public QueryCondition checkBeginEndTime() {
        Date endDate = DateUtils.parseDate(this.getEndTime());
        if(endDate == null) {
            endDate = new Date();
            this.setEndTime(DateUtils.formatDate(endDate, "yyyy-MM-dd HH:mm:ss"));
        }

        Date beginDate = DateUtils.parseDate(this.getBeginTime());
        if(beginDate == null) {
            beginDate = DateUtils.addDays(endDate, -7);
            this.setBeginTime(DateUtils.formatDate(beginDate, "yyyy-MM-dd HH:mm:ss"));
        }
        return this;
    }
}
