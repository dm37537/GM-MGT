/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.mokylin.gm.modules.system.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.mokylin.gm.entity.IEntity;

/**
 * 角色
 * @author zengweigang
 * @version 2014-06-27
 */
public class Role extends IEntity {

    private int id;
    private String name;
    private long create_by;
    private String create_date;
    private long update_by;
    private String update_date;
    private String remark;
    private short del_flag;
    private int sys_id;
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setCreate_by(long create_by){
        this.create_by=create_by;
    }
    public long getCreate_by(){
        return create_by;
    }
    public void setCreate_date(String create_date){
        this.create_date=create_date;
    }
    public String getCreate_date(){
        return create_date;
    }
    public void setUpdate_by(long update_by){
        this.update_by=update_by;
    }
    public long getUpdate_by(){
        return update_by;
    }
    public void setUpdate_date(String update_date){
        this.update_date=update_date;
    }
    public String getUpdate_date(){
        return update_date;
    }
    public void setRemark(String remark){
        this.remark=remark;
    }
    public String getRemark(){
        return remark;
    }
    public void setDel_flag(short del_flag){
        this.del_flag=del_flag;
    }
    public short getDel_flag(){
        return del_flag;
    }

    public int getSys_id() {
        return sys_id;
    }

    public void setSys_id(int sys_id) {
        this.sys_id = sys_id;
    }
}


