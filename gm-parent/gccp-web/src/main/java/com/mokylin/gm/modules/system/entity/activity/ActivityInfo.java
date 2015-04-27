package com.mokylin.gm.modules.system.entity.activity;

import com.mokylin.gm.entity.IEntity;

import java.util.List;

/**
 * Created by Administrator on 2014/7/7.
 */
public class ActivityInfo extends IEntity {
    private int id;

    private int parentType;

    private int type;

    private String name;

    private int gameId;

    private String gameName;

    private String gameVersion;

    private String hasSite;

    private String remark;

    private String url;

    private List<ActivityParameter> parameters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentType() {
        return parentType;
    }

    public void setParentType(int parentType) {
        this.parentType = parentType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getHasSite() {
        return hasSite;
    }

    public void setHasSite(String hasSite) {
        this.hasSite = hasSite;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ActivityParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<ActivityParameter> parameters) {
        this.parameters = parameters;
    }
}
