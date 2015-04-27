package com.mokylin.gm.entity;

/**
 * 权限实体
 * Created by Administrator on 2014/7/14.
 */
public class Permission extends IEntity {

    /**
     * 权限名
     */
    private String name;

    /**
     * 类型 1数字类型; 2列表类型
     */
    private String type;

    /**
     * 取值,为1
     */
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
