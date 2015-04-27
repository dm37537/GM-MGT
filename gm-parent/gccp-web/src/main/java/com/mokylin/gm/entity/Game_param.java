package com.mokylin.gm.entity;

public class Game_param extends IEntity{
    private int id;
    private String name;
    private String val;
    private String create_time;
    private int game_id;
    private String comment;
    private byte type;
    private long super_id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public long getSuper_id() {
        return super_id;
    }

    public void setSuper_id(long super_id) {
        this.super_id = super_id;
    }
}

