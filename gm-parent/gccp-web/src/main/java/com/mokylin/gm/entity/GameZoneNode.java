package com.mokylin.gm.entity;

/**
 * 游戏区节点，用于返回客户端
 * Created by Administrator on 2014/7/15.
 */
public class GameZoneNode extends IEntity{

    //游戏id
    private int super_id;

    // 游戏区Id
    private long id;

    // 游戏区名称
    private String name;

    // 父节点id
    private int parentId;

    // 状态: 0 为开服; 1 正常; 2 合服; 3 连服
    private byte status;

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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public int getSuper_id() {
        return super_id;
    }

    public void setSuper_id(int super_id) {
        this.super_id = super_id;
    }
}
