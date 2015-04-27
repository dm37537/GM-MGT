package com.mokylin.gm.modules.system.entity.action;

import com.mokylin.gm.entity.IEntity;
import com.mokylin.gm.modules.system.entity.Menu;

import java.util.List;

/**
 * Created by Administrator on 2014/7/1.
 */
public class Action extends IEntity{

    // 编号
    private int id;

    // 菜单
    private Menu menu;

    // 游戏Id
    private int gameId;

    // 游戏名称
    private String gameName;

    // 游戏版本
    private String gameVersion;

    // 特殊代理商site
    private String hasSite;

    // 名称
    private String name;

    // 别称
    private String keyName;

    // url
    private String url;

    // script
    private String script;

    // 数据处理适配器
    private String adapterClass;

    // 创建者
    private String createBy;

    // 创建时间
    private String createDate;

    private List<Parameter> parameters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
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

    public String getHasSite() {
        return hasSite;
    }

    public void setHasSite(String hasSite) {
        this.hasSite = hasSite;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getAdapterClass() {
        return adapterClass;
    }

    public void setAdapterClass(String adapterClass) {
        this.adapterClass = adapterClass;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
