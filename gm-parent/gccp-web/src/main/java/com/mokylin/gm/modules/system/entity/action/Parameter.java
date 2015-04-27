package com.mokylin.gm.modules.system.entity.action;

import com.mokylin.gm.entity.IEntity;
import com.mokylin.gm.modules.system.entity.ParameterType;

import java.util.List;

/**
 * 功能参数 实体配置
 * Created by Administrator on 2014/7/3.
 */
public class Parameter extends IEntity{

    private int actionId;

    private int id;

    private String key;

    private String label;

    private ParameterType type;

    private boolean require;

    private String min;

    private String max;

    private String version;

    private String hasSite;

    private String remark;

    private List<ActionParameterValue> values;

    public Parameter() {
        super();
    }

    public Parameter(int actionId) {
        super();
        this.actionId = actionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
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

    public List<ActionParameterValue> getValues() {
        return values;
    }

    public void setValues(List<ActionParameterValue> values) {
        this.values = values;
    }
}
