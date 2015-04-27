package com.mokylin.gm.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mokylin.gm.entity.GameZone;
import com.mokylin.gm.entity.GameZoneNode;
import com.mokylin.gm.entity.Permission;
import com.mokylin.gm.modules.system.dao.GameDao;
import com.mokylin.gm.modules.system.entity.Game;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.service.GameZoneService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 游戏列表工具类
 * Created by Administrator on 2014/6/30.
 */
public class GameUtils {

    public static final String CURRENT_GAME = "CURRENT_GAME";

    private static GameDao gameDao = SpringContextHolder.getBean(GameDao.class);

    private static GameZoneService gameZoneService = SpringContextHolder.getBean(GameZoneService.class);

    /**
     * 获取有权限的游戏区列表
     * @return
     */
    public static List<Game> getGameList() {
        Permission permission = UserUtils.getGameAreaPermission();
        Set<Integer> gameIdSet = Sets.newHashSet();
        List<Game> gameList = gameDao.findAllGame();

        if(permission != null && StringUtils.isNotBlank(permission.getValue())) {
            String[] gameIds = permission.getValue().split("[,]");
            for (String gameId:gameIds) {
                if(StringUtils.isNotBlank(gameId)) {
                    gameIdSet.add(Integer.valueOf(gameId.substring(gameId.lastIndexOf("_") + 1)));
                }
            }

            Iterator<Game> it = gameList.iterator();
            Game game;
            while (it.hasNext()){
                game = it.next();
                if(!gameIdSet.contains(game.getGameId())){
                    it.remove();
                }
            }
            return gameList;
        }
        return null;
    }

    // 从session获取当前选择游戏版本
    public static String getCurrentGame(){
        Session session = SecurityUtils.getSubject().getSession();
        String currentGame = (String) session.getAttribute(CURRENT_GAME);
        if(StringUtils.isEmpty( currentGame)){
            List<Game> list = getGameList();
            if(null != list && list.size() >=1 ){
                session.setAttribute(CURRENT_GAME, list.get(0).getGameName());
                return list.get(0).getGameName();
            }
            else
                return "common.not.has";
        }
        return currentGame;
    }

    public static int getGameId() {
        Session session = SecurityUtils.getSubject().getSession();
        String currentGame = (String) session.getAttribute(CURRENT_GAME);
        return gameDao.getGameByName(currentGame).getGameId();
    }

    // 获取游戏区列表
    public static List<GameZoneNode> getGameZoneList(String showStatus){
        return gameZoneService.getGameZoneListByGame(showStatus);
    }
}
