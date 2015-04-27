package com.mokylin.gm.config;

import com.mokylin.gm.utils.PropertiesLoader;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 全局配置类
 * Time:2014/6/9 21:30
 */
public class Global {
    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader propertiesLoader = new PropertiesLoader("gm.properties");

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null){
            value = propertiesLoader.getProperty(key);
            map.put(key, value);
        }
        return value;
    }

    /////////////////////////////////////////////////////////
    /**
     * 获取后台根路径
     */
    public static String getAdminPath() {
        return getConfig("adminPath");
    }

    /**
     * 获取URL后缀
     */
    public static String getUrlSuffix() {
        return getConfig("urlSuffix");
    }

    /**
     * 获取CKFinder上传文件的根目录
     * @return
     */
    public static String getCkBaseDir() {
        String dir = getConfig("userfiles.basedir");
        if(!dir.endsWith("/")) {
            dir += "/";
        }
        return dir;
    }
}
