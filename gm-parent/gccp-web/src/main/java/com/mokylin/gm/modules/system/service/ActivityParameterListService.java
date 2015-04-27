package com.mokylin.gm.modules.system.service;

import com.google.common.collect.Lists;
import com.mokylin.gm.modules.system.dao.activity.ActivityParameterListDao;
import com.mokylin.gm.modules.system.entity.activity.ActivityParameter;
import com.mokylin.gm.modules.system.entity.activity.ActivityParameterValue;
import com.mokylin.gm.service.base.BaseGmService;
import com.mokylin.gm.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2014/7/8.
 */
@Service
public class ActivityParameterListService extends BaseGmService{

    @Resource
    private ActivityParameterListDao parameterListDao;

    /**
     * 根据paramId,查找集合参数列表
     * @param paramId
     * @return
     */
    public List<ActivityParameterValue> findActivityParameterValues(int paramId) {
        return parameterListDao.findParameterValue(paramId);
    }

    /**
     * 保存集合参数列表
     * @param parameter
     * @return
     */
    public boolean saveActivityParameterValues(ActivityParameter parameter) {
        filterInvalidParameter(parameter);
        if(parameterListDao.deleteParameterValue(Lists.asList(parameter, new ActivityParameter[]{}))){
            return parameterListDao.saveActivityParameterValues(parameter);
        }
        return false;
    }

    /**
     * 过滤无效数据
     */
    private ActivityParameter filterInvalidParameter(ActivityParameter parameter) {
        if(null == parameter.getValues())
            return parameter;
        Iterator<ActivityParameterValue> iterator = parameter.getValues().iterator();
        ActivityParameterValue value = null;
        while (iterator.hasNext()) {
            value = iterator.next();
            if(invalidParameterValue(value)) {
                iterator.remove();
            }
        }
        return parameter;
    }

    /**
     * 判断无效数据
     */
    private boolean invalidParameterValue(ActivityParameterValue value) {
        if(StringUtils.isBlank(value.getLabel()) || StringUtils.isBlank(value.getValue()))
            return true;
        return false;
    }
}
