package com.mokylin.gm.modules.customersystem.entity;

import java.util.Date;
public class WorkOrderFlow {
	private long id;
	private long order_id;
	private String deliver_name;
	private Date deliver_time;
	private String deliver_remark;
	private String receiver_name;
	private Date receive_time;
	private int type;
	private Date deliver_expenditure_time;
	private Date handle_expenditure_time;
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
	public void setDeliver_name(String deliver_name){
		this.deliver_name=deliver_name;
	}
	public String getDeliver_name(){
		return deliver_name;
	}
	public void setDeliver_time(Date deliver_time){
		this.deliver_time=deliver_time;
	}
	public Date getDeliver_time(){
		return deliver_time;
	}
	public void setDeliver_remark(String deliver_remark){
		this.deliver_remark=deliver_remark;
	}
	public String getDeliver_remark(){
		return deliver_remark;
	}
	public void setReceiver_name(String receiver_name){
		this.receiver_name=receiver_name;
	}
	public String getReceiver_name(){
		return receiver_name;
	}
	public void setReceive_time(Date receive_time){
		this.receive_time=receive_time;
	}
	public Date getReceive_time(){
		return receive_time;
	}
	public void setType(int type){
		this.type=type;
	}
	public int getType(){
		return type;
	}
	public void setDeliver_expenditure_time(Date deliver_expenditure_time){
		this.deliver_expenditure_time=deliver_expenditure_time;
	}
	public Date getDeliver_expenditure_time(){
		return deliver_expenditure_time;
	}
	public void setHandle_expenditure_time(Date handle_expenditure_time){
		this.handle_expenditure_time=handle_expenditure_time;
	}
	public Date getHandle_expenditure_time(){
		return handle_expenditure_time;
	}
}

