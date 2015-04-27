package com.mokylin.gm.modules.system.entity;

public class User_config {
	private int id;
	private int user_id;
	private int config_item_id;
	private String config_item_value;
	private String user_config_created_date;
    private int type;
    private int sys_id;

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


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_config_created_date() {
        return user_config_created_date;
    }

    public void setUser_config_created_date(String user_config_created_date) {
        this.user_config_created_date = user_config_created_date;
    }

    public int getSys_id() {
        return sys_id;
    }

    public void setSys_id(int sys_id) {
        this.sys_id = sys_id;
    }
}

