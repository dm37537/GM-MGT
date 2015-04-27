package com.mokylin.gm.modules.system.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mokylin.gm.entity.IEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 菜单实体
 * Created by yongbo.chen
 * Time:2014/6/14 17:49
 */
@Table(name = "t_sys_menu")
public class Menu extends IEntity {

    public static final String MENU_SEPARATE = ",";

    private Menu parent;

    private int id;
    private int parentId;
    private String parentIds;
    private String name;
    private String href;
    private String target;
    private String icon;
    private int sort;
    private boolean show;
    private String permission;
    private String description;

    // 拥有子菜单列表
    private List<Menu> childList = Lists.newArrayList();

    // 对用的游戏版本
    private Map<String, String> gameVersion = Maps.newHashMap();

    public Menu() {
        super();
        this.sort = 1;
    }

    public Menu(int id) {
        this();
        this.id = id;
    }

    /**
     * 组装menu设置父子菜单
     * @param menuList
     */
    public static void assembledMenu(List<Menu> menuList) {
        for (Menu menu : menuList) {
            for (Menu item : menuList) {
                // 设置父菜单
                if(menu.getParentId() == item.getId()) {
                    menu.setParent(item);
                }

                // 设置子菜单
                if(menu.getId() == item.getParentId()) {
                    menu.getChildList().add(menu);
                }
            }
        }
    }

    /**
     * 按照父子关系组装菜单
     * @return
     */
    public static void sortList(List<Menu> list, List<Menu> sourceList, int parentId){
        for (int i = 0; i < sourceList.size(); i++){
            Menu e = sourceList.get(i);
            if(e.getParent() != null && e.getParent().getId() == parentId){
                list.add(e);
                // 判断时候还有子节点,有则继续取子节点
                for(int j = 0; j <sourceList.size() ; j++) {
                    Menu child = sourceList.get(j);
                    if(child.getParent()!= null && child.getParent().getId() == e.getId()){
                        sortList(list, sourceList, e.getId());
                        break;
                    }
                }
            }
        }
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public boolean isRoot() {
        return isRoot(this.id);
    }

    public static boolean isRoot(int id) {
        return id == 1;
    }

    // ========== get set============
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Length(min = 1, max = 3000)
    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    @Length(min = 1, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 500)
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Length(min = 0, max = 20)
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Length(min = 0, max = 100)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @NotNull
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @NotNull
    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    @Length(min = 0, max = 200)
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Length(min = 0, max = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Menu> getChildList() {
        return childList;
    }

    public void setChildList(List<Menu> childList) {
        this.childList = childList;
    }

    public Map<String, String> getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(Map<String, String> gameVersion) {
        this.gameVersion = gameVersion;
    }
}
