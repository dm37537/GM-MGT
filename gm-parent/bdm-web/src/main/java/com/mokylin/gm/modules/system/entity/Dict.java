package com.mokylin.gm.modules.system.entity;

import com.mokylin.gm.entity.IEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 字典 实体
 * Created by yongbo.chen
 * Time:2014/6/14 18:29
 */
@Table(name = "sys_dict")
public class Dict extends IEntity {

    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final int IS_SHOW = 1;
    public static final int NO_SHOW = 0;
    // 标签名
    private String label;

    // 数据值
    private String value;

    // 类型
    private String type;

    // 描述
    private String description;

    // 排序
    private int sort;

    @Length(min = 1, max = 100)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Length(min = 1, max = 100)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Length(min = 1, max = 100)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(min = 0, max = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
