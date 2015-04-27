package com.mokylin.gm.modules.customersystem.entity;

import java.util.Date;
public class WorkOrderReply {
	private long id;
	private long order_id;
	private String author;
	private Date create_time;
	private String content;
	private String receiver;
	private byte type;
	public void setId(long id){
		this.id=id;
	}
	public long getId(){
		return id;
	}
	public void setOrder_id(long order_id){
		this.order_id=order_id;
	}
	public long getOrder_id(){
		return order_id;
	}
	public void setAuthor(String author){
		this.author=author;
	}
	public String getAuthor(){
		return author;
	}
	public void setCreate_time(Date create_time){
		this.create_time=create_time;
	}
	public Date getCreate_time(){
		return create_time;
	}
	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}
	public void setReceiver(String receiver){
		this.receiver=receiver;
	}
	public String getReceiver(){
		return receiver;
	}
	public void setType(byte type){
		this.type=type;
	}
	public byte getType(){
		return type;
	}
}

