package com.mokylin.gm.modules.system.entity;

import com.mokylin.gm.entity.IEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 国际化信息实体
 * Created by Administrator on 2014/6/19.
 */
public class ResourceMessage extends IEntity{

    // key
    private String key;

    // value
    private String value;

    // 语言
    private String locale;

    // 绑定模块
    private String bundle;

    public ResourceMessage() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getBundle() {
        return bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getKey()).append(getLocale()).append(getBundle()).toHashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof ResourceMessage)
            return false;
        if(this == obj)
            return true;
        ResourceMessage  message = (ResourceMessage) obj;
        return new EqualsBuilder().append(getKey(), message.getKey()).append(getLocale(), message.getLocale()).append(getBundle(), message.getBundle()).isEquals();
    }

}
