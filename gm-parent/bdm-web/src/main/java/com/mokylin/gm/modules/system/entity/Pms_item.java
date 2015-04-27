package com.mokylin.gm.modules.system.entity;
/**
 * 系统权限项目
 */
import com.mokylin.gm.entity.IEntity;

import java.util.Date;
import java.util.List;

public class Pms_item extends IEntity{
	private int id;
	private String pms_item_title;
	private String pms_item_name;
	private byte pms_item_type;
	private String pms_item_source;
	private int pms_item_parent_id;
	private String pms_item_comment;
	private byte pms_item_state;
	private String pms_item_created_date;
	private int pms_item_sequence;
    private int sys_id;
    //附件字段//
    private boolean checked=false;
    private String configValue;
    //附件字段//

	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setPms_item_title(String pms_item_title){
		this.pms_item_title=pms_item_title;
	}
	public String getPms_item_title(){
		return pms_item_title;
	}
	public void setPms_item_name(String pms_item_name){
		this.pms_item_name=pms_item_name;
	}
	public String getPms_item_name(){
		return pms_item_name;
	}
	public void setPms_item_type(byte pms_item_type){
		this.pms_item_type=pms_item_type;
	}
	public byte getPms_item_type(){
		return pms_item_type;
	}
	public void setPms_item_source(String pms_item_source){
		this.pms_item_source=pms_item_source;
	}
	public String getPms_item_source(){
		return pms_item_source;
	}
	public void setPms_item_parent_id(int pms_item_parent_id){
		this.pms_item_parent_id=pms_item_parent_id;
	}
	public int getPms_item_parent_id(){
		return pms_item_parent_id;
	}
	public void setPms_item_comment(String pms_item_comment){
		this.pms_item_comment=pms_item_comment;
	}
	public String getPms_item_comment(){
		return pms_item_comment;
	}
	public void setPms_item_state(byte pms_item_state){
		this.pms_item_state=pms_item_state;
	}
	public byte getPms_item_state(){
		return pms_item_state;
	}
	public void setPms_item_created_date(String pms_item_created_date){
		this.pms_item_created_date=pms_item_created_date;
	}
	public String getPms_item_created_date(){
		return pms_item_created_date;
	}
	public void setPms_item_sequence(int pms_item_sequence){
		this.pms_item_sequence=pms_item_sequence;
	}
	public int getPms_item_sequence(){
		return pms_item_sequence;
	}
    public int getSys_id() {
        return sys_id;
    }
    public void setSys_id(int sys_id) {
        this.sys_id = sys_id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public static void sortList(List<Pms_item> list, List<Pms_item> sourcelist, int parentId){
        for (int i=0; i<sourcelist.size(); i++){
            Pms_item item = sourcelist.get(i);
            if (item.getPms_item_parent_id()==parentId){
                list.add(item);
                // 判断是否还有子节点, 有则继续获取子节点
                for (int j=0; j<sourcelist.size(); j++){
                    Pms_item sonItem = sourcelist.get(j);
                    if (sonItem.getPms_item_parent_id()==item.getId()){
                        sortList(list, sourcelist, item.getId());
                        break;
                    }
                }
            }
        }
    }
}

