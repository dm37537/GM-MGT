package com.mokylin.gm.modules.system.entity.action;

import com.mokylin.gm.entity.IEntity;

/**
 * Created by Administrator on 2014/7/7.
 */
public class ActionParameterValue extends IEntity {

    private String label;

    private String value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
