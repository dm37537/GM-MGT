package com.mokylin.gm.component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mokylin.gm.entity.GameZone;
import com.mokylin.gm.entity.GameZoneNode;
import com.mokylin.gm.utils.BdpSpi;
import com.mokylin.gm.utils.CacheUtils;
import com.mokylin.gm.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/7/14.
 */
public class GameZoneManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameZoneManager.class);

    private static Map<Integer, Map<Long, GameZone>> gameZoneMap;

    private static File timeFile;

    public static void init() {
        timeFile = new File(Thread.currentThread().getContextClassLoader().getResource(".").getPath().toString(), "updatetime");

        gameZoneMap = (Map<Integer, Map<Long, GameZone>>) CacheUtils.get(CacheUtils.GAME_ZONE_CACHE, "gameZone");
        if (gameZoneMap == null) {
            gameZoneMap = Maps.newHashMap();
            downLoadAllGameZoneInfo();
            CacheUtils.put(CacheUtils.GAME_ZONE_CACHE, "gameZone", gameZoneMap);
        }
    }

    /**
     * 拉取所有游戏区
     */
    private static void downLoadAllGameZoneInfo() {
        LOGGER.info("开始拉取全部游戏区数据");
        String ret = new BdpSpi().downLoadAllGameZoneInfo();
        //归类分组
        makeInject(ret);
    }

    /**
     * 更新游戏区信息
     */
    public static void updateGameZone() {
        LOGGER.info("开始更新游戏区数据");
        try {
            if(!timeFile.exists())
                timeFile.createNewFile();
            String lastDate = FileUtils.readFileToString(timeFile, Charset.forName("utf-8"));
            lastDate = StringUtils.isBlank(lastDate)?"2000-1-1 00:00:00":lastDate;
            String ret = new BdpSpi().updateGameZoneInfo(lastDate);

            // 归类分组
            lastDate = makeInject(ret);
            // 存储最后更新时间
            if(StringUtils.isNotBlank(lastDate))
                FileUtils.write(timeFile, lastDate);
        } catch (IOException e) {
            LOGGER.error("Exception:{}", e.getMessage(), e);
        }
    }

    /**
     * 更新游戏区map
     *
     * @param jsonStr
     */
    private static String makeInject(String jsonStr) {
        String lastDate = "";
        if (StringUtils.isNotBlank(jsonStr)) {
            List<GameZone> gameZones = JSON.parseArray(jsonStr, GameZone.class);

            Iterator<GameZone> it = gameZones.iterator();
            GameZone gameZone;
            Map<Long, GameZone> classifyMap;
            while (it.hasNext()) {
                gameZone = it.next();
                //获取这批数据的最大时间
                if(gameZone.getUpdate_time().compareToIgnoreCase(lastDate) > 1){
                    lastDate = gameZone.getUpdate_time();
                }

                if (gameZoneMap.containsKey(gameZone.getSuper_id())) {
                    classifyMap = gameZoneMap.get(gameZone.getSuper_id());

                    if (classifyMap.containsKey(gameZone.getId())) {
                        classifyMap.remove(gameZone.getId());
                    }
                    classifyMap.put(gameZone.getId(), gameZone);
                } else {
                    classifyMap = Maps.newHashMap();
                    classifyMap.put(gameZone.getId(), gameZone);
                    gameZoneMap.put(gameZone.getSuper_id(), classifyMap);
                }
            }
        }
        return lastDate;
    }

    public static Map<Integer, List<GameZoneNode>> getGameZoneByUser(String permissionValue) {
        Map<Long, GameZoneNode> nodeMap = Maps.newHashMap();
        Map<Integer, List<GameZoneNode>> nodes = Maps.newHashMap();

        String[] permissionGameIds = permissionValue.split("[,]");
        String[] id_superId;
        GameZone gameZone;
        for (String permissionGameId : permissionGameIds) {
            if (StringUtils.isNotBlank(permissionGameId)) {
                id_superId = permissionGameId.split("[_]");
                if (id_superId.length >= 2 && gameZoneMap.containsKey(Integer.valueOf(id_superId[1]))) {
                    //添加叶子节点
                    int superId = Integer.valueOf(id_superId[1]);
                    int parentId = Integer.valueOf(id_superId[0]);
                    Map<Long, GameZone> map = gameZoneMap.get(superId);
                    Iterator<Long> keyIt = map.keySet().iterator();
                    while (keyIt.hasNext()) {
                        gameZone = map.get(keyIt.next());
                        if (gameZone.getParent_id() == parentId)
                            nodeMap.put(gameZone.getId(), generateGameZoneNode(gameZone));
                    }

                    //添加当前节点和所有父节点
                    addParent(nodeMap, Long.valueOf(parentId), superId);
                }
            }
        }

        // 按照游戏superId分组
        Iterator<Long> keys = nodeMap.keySet().iterator();
        GameZoneNode tmp;
        List<GameZoneNode> list;
        while (keys.hasNext()) {
            tmp = nodeMap.get(keys.next());
            if (tmp.getSuper_id() == 0)
                continue;
            if (nodes.containsKey(tmp.getSuper_id())) {
                nodes.get(tmp.getSuper_id()).add(tmp);
            } else {
                list = Lists.newArrayList();
                list.add(tmp);
                nodes.put(tmp.getSuper_id(), list);
            }
        }
        return nodes;
    }

    private static void addParent(Map<Long, GameZoneNode> nodeMap, Long parentId, int superId) {
        GameZone parent = gameZoneMap.get(superId).get(parentId);
        if (parent == null)
            return;
        if (!nodeMap.containsKey(parent.getId())) {
            nodeMap.put(parent.getId(), generateGameZoneNode(parent));
            addParent(nodeMap, Long.valueOf(parent.getParent_id()), parent.getSuper_id());
        }
    }

    private static GameZoneNode generateGameZoneNode(GameZone gameZone) {
        GameZoneNode node = new GameZoneNode();
        node.setId(gameZone.getId());
        node.setName(gameZone.getName());
        node.setParentId(gameZone.getParent_id());
        node.setStatus(gameZone.getStatus());
        node.setSuper_id(gameZone.getSuper_id());

        return node;
    }
}
