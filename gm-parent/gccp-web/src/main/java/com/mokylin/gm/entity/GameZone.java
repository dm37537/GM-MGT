package com.mokylin.gm.entity;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 游戏区 实体
 * Created by Administrator on 2014/7/11.
 */
public class GameZone extends IEntity {

    //游戏id
    private int super_id;

    // 游戏区Id
    private long id;

    // 游戏区名称
    private String name;

    // 父节点id
    private int parent_id;

    // 状态: 0 为开服; 1 正常; 2 合服; 3 连服
    private byte status;

    private int level;

    private List<Game_param> game_params = Lists.newArrayList();

    // 游戏区标识
    private String key;

    // 数据最后更新时间
    private String update_time;

    public GameZone() {
        super();
    }

    public GameZone(long id, String name, int parent_id, byte status) {
        super();
        this.id = id;
        this.name = name;
        this.parent_id = parent_id;
        this.status = status;
    }

    public int getSuper_id() {
        return super_id;
    }

    public void setSuper_id(int super_id) {
        this.super_id = super_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Game_param> getGame_params() {
        return game_params;
    }

    public void setGame_params(List<Game_param> game_params) {
        this.game_params = game_params;
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
}
