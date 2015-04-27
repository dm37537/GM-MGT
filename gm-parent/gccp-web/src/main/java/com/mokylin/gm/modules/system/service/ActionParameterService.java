package com.mokylin.gm.modules.system.service;

import com.google.common.collect.Lists;
import com.mokylin.gm.modules.system.dao.action.ActionParameterDao;
import com.mokylin.gm.modules.system.dao.action.ActionParameterListDao;
import com.mokylin.gm.modules.system.entity.ParameterType;
import com.mokylin.gm.modules.system.entity.action.Parameter;
import com.mokylin.gm.service.base.BaseGmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2014/7/9.
 */
@Service
public class ActionParameterService extends BaseGmService{

    @Resource
    private ActionParameterDao parameterDao;

    @Resource
    private ActionParameterListDao parameterListDao;

    /**
     * 查找Parameter列表信息
     */
    public List<Parameter> findActionParametersByActionId(Integer actionId) {
        List<Parameter> parameterList = parameterDao.findParametersByActionId(actionId);
        return parameterList;
    }

    /**
     * 查找Parameter信息，根据paramId
     */
    public Parameter findActionParameterByParamId(Integer paramId) {
        return parameterDao.findParametersByParamId(paramId);
    }

    /**
     * 添加参数信息
     * @param parameter
     * @return
     */
    public boolean addActionParameter(Parameter parameter) {
        return parameterDao.addParameter(parameter);
    }

    /**
     * 修改参数信息
     * @param parameter
     * @return
     */
    public boolean updateActionParameter(Parameter parameter) {
        return parameterDao.updateActionParameter(parameter);
    }


    public boolean deleteActionParameter(Integer paramId) {
        boolean ret = true;
        //先获取参数
        Parameter parameter = parameterDao.findParametersByParamId(paramId);
        if(null != parameter){
            if(parameter.getType().equals(ParameterType.Collection)){
                parameterListDao.deleteParameterValue(Lists.asList(parameter, new Parameter[]{}));
            }
            return ret && parameterDao.deleteParametersByParamId(paramId);
        }
        return ret;
    }

}
