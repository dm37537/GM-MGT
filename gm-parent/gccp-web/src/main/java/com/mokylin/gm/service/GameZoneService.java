package com.mokylin.gm.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mokylin.gm.component.GameZoneManager;
import com.mokylin.gm.entity.GameZoneNode;
import com.mokylin.gm.entity.Permission;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.utils.GameUtils;
import com.mokylin.gm.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2014/7/11.
 */
@Service
public class GameZoneService extends BaseService{

    private static final String CACHE_GAME_ZONE = "gameZoneList";

    /**
     * 根据游戏 获取游戏区列表
     * @return
     */
    public List<GameZoneNode> getGameZoneListByGame(String showStatus) {
        Map<Integer,List<GameZoneNode>> gameZones = (Map<Integer,List<GameZoneNode>>)UserUtils.getCache(CACHE_GAME_ZONE);
        if(gameZones == null || gameZones.size() == 0) {
            Permission permission = UserUtils.getGameAreaPermission();
            if (permission != null && StringUtils.isNotBlank(permission.getValue())) {
                gameZones = GameZoneManager.getGameZoneByUser(permission.getValue());
                UserUtils.putCache(CACHE_GAME_ZONE, gameZones);
            }
            else{
                gameZones = Maps.newHashMap();
            }
        }

        List<GameZoneNode> gameList = Lists.newArrayList();
        if(gameZones.containsKey(GameUtils.getGameId()))
            // 采用复制
            gameList.addAll(gameZones.get(GameUtils.getGameId()));

        // 过滤状态
        if (StringUtils.isNotBlank(showStatus)) {
            Iterator<GameZoneNode> it = gameList.iterator();
            while (it.hasNext()) {
                if (-1 == showStatus.indexOf(String.valueOf(it.next().getStatus()))) {
                    it.remove();
                }
            }
        }
        return gameList;
    }
}
