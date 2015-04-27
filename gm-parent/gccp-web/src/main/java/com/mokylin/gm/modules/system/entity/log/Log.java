package com.mokylin.gm.modules.system.entity.log;

import com.mokylin.gm.entity.IEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 日志Entity
 *
 * @author yongbo.chen
 * @version 2014-06-15
 */
@Entity
@Table(name = "t_sys_log")
public class Log extends IEntity {

    private static final long serialVersionUID = 1L;
    private String type;        // 日志类型（1：接入日志；2：错误日志）
    private String createBy;        // 创建者
    private Date createDate;    // 日志创建时间
    private String remoteAddr;    // 操作用户的IP地址
    private String requestUri;    // 操作的URI
    private String method;        // 操作的方式
    private String params;        // 操作提交的数据
    private String userAgent;    // 操作用户代理信息
    private String exception;    // 异常信息

    public static final String TYPE_ACCESS = "Normal";
    public static final String TYPE_EXCEPTION = "Exception";

    public Log() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}