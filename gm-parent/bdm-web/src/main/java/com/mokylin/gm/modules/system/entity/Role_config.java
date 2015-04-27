package com.mokylin.gm.modules.system.entity;

import java.util.Date;
public class Role_config {
	private int id;
	private int role_id;
	private int config_item_id;
	private String config_item_value;
	private String role_config_created_date;
    private int type;
	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setRole_id(int role_id){
		this.role_id=role_id;
	}
	public int getRole_id(){
		return role_id;
	}
	public void setConfig_item_id(int config_item_id){
		this.config_item_id=config_item_id;
	}
	public int getConfig_item_id(){
		return config_item_id;
	}
	public void setConfig_item_value(String config_item_value){
		this.config_item_value=config_item_value;
	}
	public String getConfig_item_value(){
		return config_item_value;
	}
	public void setRole_config_created_date(String role_config_created_date){
		this.role_config_created_date=role_config_created_date;
	}
	public String getRole_config_created_date(){
		return role_config_created_date;
	}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

