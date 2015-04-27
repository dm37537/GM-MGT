package com.mokylin.gm.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cache工具类
 * @author yongbo.chen
 * @version 2013-5-29
 */
public class CacheUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheUtils.class);

	private static CacheManager cacheManager = ((CacheManager)SpringContextHolder.getBean("cacheManager"));

    private static final String SYS_CACHE = "sysCache";

    /**
     * 游戏区缓存
     */
    public static final String GAME_ZONE_CACHE = "gameZoneCache";

    /**
     * 获取一个新的cache，没有就创建一个
     * @param cacheName
     * @return
     */
    private static Cache getCache(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        if(cache == null){
            cacheManager.addCache(cacheName);
            cache = cacheManager.getCache(cacheName);
            cache.getCacheConfiguration().setEternal(true);
        }
        return cache;
    }

    public static CacheManager getCacheManager(){
        return cacheManager;
    }

    public static Object get(String key){
        return get(SYS_CACHE, key);
    }

    public static void put(String key, Object value){
        put(SYS_CACHE, key, value);
    }

    public static void remove(String key){
        remove(SYS_CACHE, key);
    }

    public static Object get(String cacheName, String key) {
        Element element = getCache(cacheName).get(key);
        return element==null?null:element.getObjectValue();
    }

    public static void put(String cacheName, String key, Object value) {
        Element element = new Element(key, value);
        getCache(cacheName).put(element);
    }

    public static void remove(String cacheName, String key) {
        getCache(cacheName).remove(key);
    }
}
