package com.mokylin.gm.modules.system.service;

import com.google.common.collect.Lists;
import com.mokylin.gm.modules.system.dao.activity.ActivityParameterDao;
import com.mokylin.gm.modules.system.dao.activity.ActivityParameterListDao;
import com.mokylin.gm.modules.system.entity.ParameterType;
import com.mokylin.gm.modules.system.entity.activity.ActivityParameter;
import com.mokylin.gm.service.base.BaseGmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2014/7/8.
 */
@Service
public class ActivityParameterService extends BaseGmService{

    @Resource
    private ActivityParameterDao parameterDao;

    @Resource
    private ActivityParameterListDao parameterListDao;

    /**
     * 查找ActivityParameter列表信息
     * @param id
     * @return
     */
    public List<ActivityParameter> findActivityParameterByActivityId(Integer id) {
        List<ActivityParameter>  parameterList = parameterDao.findParameterByActivityId(id);
//        for (ActivityParameter parameter: parameterList){
//            // 集合类型, 设置集合值
//            if(parameter.getType().equals(ParameterType.Collection)){
//                parameter.setValues(parameterListDao.findParameterValue(parameter.getId()));
//            }
//        }
        return parameterList;
    }

    /**
     * 查找ActivityParameter信息，根据paramId
     */
    public ActivityParameter findActivityParameterByParamId(Integer paramId) {
        return parameterDao.findParameterByParamId(paramId);
    }

    /**
     * 添加参数信息
     * @param parameter
     * @return
     */
    public boolean addActivityParameter(ActivityParameter parameter) {
        return parameterDao.addActivityParameter(parameter);
    }

    /**
     * 修改参数信息
     * @param parameter
     * @return
     */
    public boolean updateActivityParameter(ActivityParameter parameter) {
        return parameterDao.updateActivityParameter(parameter);
    }


    public boolean deleteActivityParameter(Integer paramId) {
        boolean ret = true;
        //先获取参数
        ActivityParameter parameter = parameterDao.findParameterByParamId(paramId);
        if(null != parameter){
            if(parameter.getType().equals(ParameterType.Collection)){
                parameterListDao.deleteParameterValue(Lists.asList(parameter, new ActivityParameter[]{}));
            }
            return ret && parameterDao.deleteParametersByParamId(paramId);
        }
        return ret;
    }
}
