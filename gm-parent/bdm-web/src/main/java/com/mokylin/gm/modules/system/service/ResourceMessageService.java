package com.mokylin.gm.modules.system.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mokylin.gm.modules.system.dao.ResourceMessageDao;
import com.mokylin.gm.modules.system.entity.ResourceMessage;
import com.mokylin.gm.service.BaseGmService;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.ResourceManager;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 国际化资源 service
 * Created by Administrator on 2014/6/19.
 */
@Service
public class ResourceMessageService extends BaseGmService{

    @Resource
    private ResourceMessageDao resourceMessageDao;

    /**
     * 获取所有国际化信息
     * @return
     */
    public Map<String, Map<String, List<ResourceMessage>>> getResourceMessageMap() {
        List<ResourceMessage> list = resourceMessageDao.loadResourceBundle();
        Map<String, Map<String, List<ResourceMessage>>> map = Maps.newHashMap();

        for(ResourceMessage message : list){
            Map<String, List<ResourceMessage>> keyMap;
            if(map.containsKey(message.getBundle())){
                keyMap = map.get(message.getBundle());
                if(keyMap.containsKey(message.getKey()))
                    keyMap.get(message.getKey()).add(message);
                else{
                    List<ResourceMessage> rList = Lists.newArrayList();
                    rList.add(message);
                    keyMap.put(message.getKey(),rList);
                }
            }
            else{
                keyMap = Maps.newHashMap();
                List<ResourceMessage> rList = Lists.newArrayList();
                rList.add(message);
                keyMap.put(message.getKey(), rList);
                map.put(message.getBundle(),keyMap);
            }
        }
        return map;
    }

    /**
     * 分页查询 国际化信息
     * @param page
     * @param resource
     * @return
     */
    public Page<ResourceMessage> findResourceMessage(Page<ResourceMessage> page, ResourceMessage resource) {
        return resourceMessageDao.findResourceMessage(page, resource);
    }

    public boolean deleteResourceMessage(String locale, String bundle, String key) {
//        boolean ret = resourceMessageDao.deleteResourceMessage(locale, bundle, key);
        boolean ret = true;
        if(ret)
            ResourceManager.refreshResource();
        return ret;
    }
}
