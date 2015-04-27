package com.mokylin.gm.modules.system.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.mokylin.gm.entity.IEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Game extends IEntity{
	private Long id;
	private String name;
	private byte status;
	private int parent_id;
	private int level;
	private String create_time;
    private String update_time;
    private List<Game_param> game_params= Lists.newArrayList();
    @JSONField(serialize = false)
    private boolean checked=false;
    private Long super_id;
    private String key;
	public void setId(Long id){
		this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setStatus(byte status){
		this.status=status;
	}
	public byte getStatus(){
		return status;
	}
	public void setParent_id(int parent_id){
		this.parent_id=parent_id;
	}
	public int getParent_id(){
		return parent_id;
	}
	public void setLevel(int level){
		this.level=level;
	}
	public int getLevel(){
		return level;
	}
	public void setCreate_time(String create_time){
		this.create_time=create_time;
	}
	public String getCreate_time(){
		return create_time;
	}

    public List<Game_param> getGame_params() {
        return game_params;
    }

    public void setGame_params(List<Game_param> game_params) {
        this.game_params = game_params;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Long getSuper_id() {
        return super_id;
    }

    public void setSuper_id(Long super_id) {
        this.super_id = super_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public static void sortList(List<Game> list, List<Game> sourcelist, long parentId){
        for (int i=0; i<sourcelist.size(); i++){
            Game item = sourcelist.get(i);
            if (item.getParent_id()==parentId){
                list.add(item);
                // 判断是否还有子节点, 有则继续获取子节点
                for (int j=0; j<sourcelist.size(); j++){
                    Game son = sourcelist.get(j);
                    if (son.getParent_id()==item.getId()){
                        sortList(list, sourcelist, item.getId());
                        break;
                    }
                }
            }
        }
    }
    public static void checked(List<Game> list,String configVal){
        String[] valSz=configVal.split(",");
        List<String> configList=Arrays.asList(valSz);
        for (int i = 0; i < list.size(); i++) {
            Game game=list.get(i);
            if(game.getLevel()==2){
                for (int j = 0; j < configList.size(); j++) {
                    String val=configList.get(j);
                    if(val.equals(game.getId()+"_"+game.getSuper_id())){
                        game.setChecked(true);
                        break;
                    }
                }
            }
        }
    }
    public static void getPmsGame(List<Game> src,List<Game> target,String configVal){//获得有权限的代理商数据
        if(configVal!=null){
            List<String> configList=Arrays.asList(configVal.split(","));
            for(Game game:src){
                if(game.level!=2){
                    target.add(game);
                }else{
                    for (int j = 0; j < configList.size(); j++) {
                        String val=configList.get(j);
                        if(val.equals(game.getId()+"_"+game.getSuper_id())){
                            target.add(game);
                            break;
                        }
                    }
                }
            }
        }

    }
    public static void params2Game(List<Game> games,List<Game_param> params){
        for(Game game:games){
           Iterator<Game_param> paramIt=params.iterator();
            while(paramIt.hasNext()){
                Game_param param=paramIt.next();
                if(param.getGame_id()==game.getId()){
                    game.getGame_params().add(param);
                    paramIt.remove();
                }
            }
        }
    }
}

