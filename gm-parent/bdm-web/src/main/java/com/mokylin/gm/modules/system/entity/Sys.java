package com.mokylin.gm.modules.system.entity;

import com.mokylin.gm.entity.IEntity;

import java.sql.Date;
public class Sys extends IEntity{
	private Integer id;
	private byte system_type;
	private String system_name;
	private int system_sequence;
	private String system_created_date;
    private byte system_state;
    private String ip_list;
    private String verify_code;
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setSystem_type(byte system_type){
		this.system_type=system_type;
	}
	public byte getSystem_type(){
		return system_type;
	}
	public void setSystem_name(String system_name){
		this.system_name=system_name;
	}
	public String getSystem_name(){
		return system_name;
	}
	public void setSystem_sequence(int system_sequence){
		this.system_sequence=system_sequence;
	}
	public int getSystem_sequence(){
		return system_sequence;
	}
	public void setSystem_created_date(String system_created_date){
		this.system_created_date=system_created_date;
	}
	public String getSystem_created_date(){
		return system_created_date;
	}
    public byte getSystem_state() {
        return system_state;
    }

    public void setSystem_state(byte system_state) {
        this.system_state = system_state;
    }

    public void setIp_list(String ip_list) {
        this.ip_list = ip_list;
    }

    public String getIp_list() {
        return ip_list;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }
}

