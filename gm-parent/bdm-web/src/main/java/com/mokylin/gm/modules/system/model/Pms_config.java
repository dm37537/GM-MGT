package com.mokylin.gm.modules.system.model;

import com.mokylin.gm.entity.IEntity;

public class Pms_config extends IEntity{
	private String name;
	private String value;
    private int type;

    public Pms_config(String name, String value, int type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

