package com.mokylin.gm.modules.system.utils;

import com.google.common.collect.Maps;
import com.mokylin.gm.entity.Permission;
import com.mokylin.gm.modules.system.entity.Menu;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.security.SystemAuthorizingRealm;
import com.mokylin.gm.modules.system.service.MenuService;
import com.mokylin.gm.modules.system.service.UserService;
import com.mokylin.gm.utils.SpringContextHolder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by yongbo.chen
 * Time:2014/6/15 21:22
 */
public class UserUtils {

    public static final String GameArea = "gamearea";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserUtils.class);

    public static UserService userService = SpringContextHolder.getBean(UserService.class);

    public static MenuService menuService = SpringContextHolder.getBean(MenuService.class);

    // 获取游戏区权限
    public static Permission getGameAreaPermission() {
        User user = getUser();
        if(user.getPermissions() != null)
            return UserUtils.getUser().getPermissions().get(GameArea);
        return null;
    }

    public static User getUser() {
        return userService.getUser();
    }

    public static User getUser(boolean isRefresh) {
        return userService.getUser(isRefresh);
    }


    public static User getUserById(Long id) {
        return userService.getUserById(id);
    }

    public static List<Menu> getMenuList() {
        return menuService.getMenuList(getUser());
    }

    // User Cache
    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
        Object obj = getCacheMap().get(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value) {
        getCacheMap().put(key, value);
    }

    public static void removeCache(String key) {
        getCacheMap().remove(key);
    }

    public static Map<String, Object> getCacheMap() {
        Map<String, Object> map = Maps.newHashMap();
        try {
            Subject subject = SecurityUtils.getSubject();
            SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) subject.getPrincipal();
            return principal != null ? principal.getCacheMap() : map;
        } catch (Exception e) {
            LOGGER.error("get cache exception:{}", e.getMessage(), e);
            return map;
        }
    }
}
