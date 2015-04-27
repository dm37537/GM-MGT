package com.mokylin.gm.utils;

import com.mokylin.gm.modules.system.entity.ResourceMessage;
import com.mokylin.gm.modules.system.service.ResourceMessageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 获取国际化管理类 单例
 * Created by Administrator on 2014/6/19.
 */
public class ResourceManager {

    public static String RESOURCE_LANGUAGE = "resource_language";

    private static  ResourceManager _instance;

    private Map<String, Map<String, List<ResourceMessage>>> resouceMessageMap ;

    private ResourceManager(){
    }

    private static ResourceManager getInstance() {
        if(null == _instance){
            synchronized (ResourceManager.class){
                if(null == _instance) {
                    _instance = new ResourceManager();
                    _instance.resouceMessageMap = SpringContextHolder.getBean(ResourceMessageService.class).getResourceMessageMap();
                }
            }
        }
        return _instance;
    }

    public static void refreshResource(){
        getInstance().resouceMessageMap = SpringContextHolder.getBean(ResourceMessageService.class).getResourceMessageMap();
    }

    public static String getString(String bundle, String key, Object... objs) {
        return getString("", bundle, key, objs);
    }

    public static String getString(HttpSession session, String bundle, String key, Object... objs) {
        String locale = (String)session.getAttribute(RESOURCE_LANGUAGE);
        return getString(locale, bundle , key, objs);
    }

    public static String getString(HttpServletRequest request, String bundle, String key, Object... objs) {
        String locale = (String)request.getSession().getAttribute(RESOURCE_LANGUAGE);
        return getString(locale, bundle , key, objs);
    }

    public static String getString(String locale, String bundle, String key, Object...objs) {
        if(StringUtils.isEmpty(locale))
            locale = Locale.getDefault().toString();
        Map<String, List<ResourceMessage>> keys = getInstance().resouceMessageMap.get(bundle);
        if(null != keys && null != keys.get(key)){
            for (ResourceMessage message : keys.get(key)){
                if(locale.equals(message.getLocale())){
                    return MessageFormat.format(message.getValue(),objs);
                }
            }
        }
        return "";
    }
}
