package com.mokylin.gm.modules.system.entity;

public class Server {
	private int id;
	private byte status;
	private int type;
	private String create_time;
	private String comment;
	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setStatus(byte status){
		this.status=status;
	}
	public byte getStatus(){
		return status;
	}
	public void setType(int type){
		this.type=type;
	}
	public int getType(){
		return type;
	}
	public void setCreate_time(String create_time){
		this.create_time=create_time;
	}
	public String getCreate_time(){
		return create_time;
	}
	public void setComment(String comment){
		this.comment=comment;
	}
	public String getComment(){
		return comment;
	}
}

