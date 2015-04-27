package com.mokylin.gm.modules.system.entity;

import com.mokylin.gm.entity.IEntity;
import com.mokylin.gm.entity.Permission;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import java.util.Map;

/**
 * 用户实体
 * Created by yongbo.chen
 * Time:2014/6/13 12:31
 */
@Table(name = "t_sys_user")
public class User extends IEntity {

    // 编号id
    private long id;

    // 用户名
    private String userName;

    // 密码(加密)
    private String password;

    // 用户姓名
    private String name;

    // 邮箱
    private String email;

    // 用户手机
    private String mobile;

    // 用户状态 0 停用， 1 正常
    private int status;

    // 创建时间
    private String createDate;

    // 最后登录时间
    private String lastLoginDate;

    // 最后登录IP
    private String lastLoginIp;

    // 最后修改密码时间
    private String lastModifyDate;

    // 备注
    private String description;

    private Map<String, Permission> permissions ;

    public User() {
    }

    public User(long id) {
        this.id = id;
    }

    public User(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User)
            return false;
        if (this == obj)
            return true;
        User other = (User) obj;
        return new EqualsBuilder().append(getId(), other.getId()).isEquals();
    }

    // get&set 方法
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Length(min = 1, max = 100)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Length(min = 1, max = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Length(min = 1, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Email
    @Length(min = 0, max = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min = 0, max = 50)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, Permission> permissions) {
        this.permissions = permissions;
    }
}
