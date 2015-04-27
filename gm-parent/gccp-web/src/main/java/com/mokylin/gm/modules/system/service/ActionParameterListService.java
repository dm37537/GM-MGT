package com.mokylin.gm.modules.system.service;

import com.google.common.collect.Lists;
import com.mokylin.gm.modules.system.dao.action.ActionParameterListDao;
import com.mokylin.gm.modules.system.entity.action.ActionParameterValue;
import com.mokylin.gm.modules.system.entity.action.Parameter;
import com.mokylin.gm.service.base.BaseGmService;
import com.mokylin.gm.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2014/7/9.
 */
@Service
public class ActionParameterListService extends BaseGmService{

    @Resource
    private ActionParameterListDao parameterListDao;

    /**
     * 根据paramId,查找集合参数列表
     * @param paramId
     * @return
     */
    public List<ActionParameterValue> findActionParameterValues(int paramId) {
        return parameterListDao.findParameterValue(paramId);
    }

    /**
     * 保存集合参数列表
     * @param parameter
     * @return
     */
    public boolean saveActionParameterValues(Parameter parameter) {
        filterInvalidParameter(parameter);
        if(parameterListDao.deleteParameterValue(Lists.asList(parameter, new Parameter[]{}))){
            return parameterListDao.saveActionParameterValues(parameter);
        }
        return false;
    }

    /**
     * 过滤无效数据
     */
    private Parameter filterInvalidParameter(Parameter parameter) {
        if(null == parameter.getValues())
            return parameter;
        Iterator<ActionParameterValue> iterator = parameter.getValues().iterator();
        ActionParameterValue value = null;
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
    private boolean invalidParameterValue(ActionParameterValue value) {
        if(StringUtils.isBlank(value.getLabel()) || StringUtils.isBlank(value.getValue()))
            return true;
        return false;
    }

}
