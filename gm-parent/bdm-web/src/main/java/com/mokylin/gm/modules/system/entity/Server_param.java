package com.mokylin.gm.modules.system.entity;

public class Server_param {
	private int id;
	private String name;
	private String val;
	private String create_time;
	private int server_id;
	private String comment;
	private byte type;
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
	public void setVal(String val){
		this.val=val;
	}
	public String getVal(){
		return val;
	}
	public void setCreate_time(String create_time){
		this.create_time=create_time;
	}
	public String getCreate_time(){
		return create_time;
	}
	public void setServer_id(int server_id){
		this.server_id=server_id;
	}
	public int getServer_id(){
		return server_id;
	}
	public void setComment(String comment){
		this.comment=comment;
	}
	public String getComment(){
		return comment;
	}
	public void setType(byte type){
		this.type=type;
	}
	public byte getType(){
		return type;
	}
}

