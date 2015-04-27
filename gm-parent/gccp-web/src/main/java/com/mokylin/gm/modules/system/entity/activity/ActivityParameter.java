package com.mokylin.gm.modules.system.entity.activity;

import com.mokylin.gm.entity.IEntity;
import com.mokylin.gm.modules.system.entity.ParameterType;

import java.util.List;

/**
 * Created by Administrator on 2014/7/7.
 */
public class ActivityParameter extends IEntity {

    private int id;

    // 活动id
    private int activityId;

    // 条件key, 传递给游戏
    private String gameKey;

    // 参数key，用于关联（默认与game_key相同）
    private String key;

    private String label;

    private ParameterType type;

    private boolean require;

    private String min;

    private String max;

    private String version;

    private String hasSite;

    private String remark;

    // 前置key，关联key字段
    private String premiseKey;

    private List<ActivityParameterValue> values ;

    public ActivityParameter() {
        super();
    }

    public ActivityParameter(int activityId) {
        super();
        this.activityId = activityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getGameKey() {
        return gameKey;
    }

    public void setGameKey(String gameKey) {
        this.gameKey = gameKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ParameterType getType() {
        return type;
    }

    public void setType(ParameterType type) {
        this.type = type;
    }

    public boolean isRequire() {
        return require;
    }

    public void setRequire(boolean require) {
        this.require = require;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHasSite() {
        return hasSite;
    }

    public void setHasSite(String hasSite) {
        this.hasSite = hasSite;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPremiseKey() {
        return premiseKey;
    }

    public void setPremiseKey(String premiseKey) {
        this.premiseKey = premiseKey;
    }

    public List<ActivityParameterValue> getValues() {
        return values;
    }

    public void setValues(List<ActivityParameterValue> values) {
        this.values = values;
    }
}
