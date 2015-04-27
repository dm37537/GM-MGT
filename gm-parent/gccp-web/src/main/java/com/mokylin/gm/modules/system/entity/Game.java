package com.mokylin.gm.modules.system.entity;

import com.google.common.collect.Lists;
import com.mokylin.gm.entity.IEntity;

import javax.persistence.Table;
import java.util.List;

/**
 * 游戏 实体
 * Created by Administrator on 2014/6/30.
 */
@Table(name = "t_sys_game")
public class Game extends IEntity {

    // 游戏编号
    private int id;

    // 游戏名称
    private String gameName;

    // 运维系统游戏编号
    private int gameId;

    // 游戏所有版本
    private List<String> versions = Lists.newArrayList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }
}
