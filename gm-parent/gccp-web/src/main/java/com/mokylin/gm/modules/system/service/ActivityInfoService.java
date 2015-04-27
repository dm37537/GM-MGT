package com.mokylin.gm.modules.system.service;

import com.google.common.collect.Lists;
import com.mokylin.gm.modules.system.dao.activity.ActivityInfoDao;
import com.mokylin.gm.modules.system.dao.activity.ActivityParameterDao;
import com.mokylin.gm.modules.system.dao.activity.ActivityParameterListDao;
import com.mokylin.gm.modules.system.entity.ParameterType;
import com.mokylin.gm.modules.system.entity.activity.ActivityCondition;
import com.mokylin.gm.modules.system.entity.activity.ActivityInfo;
import com.mokylin.gm.modules.system.entity.activity.ActivityParameter;
import com.mokylin.gm.service.base.BaseGmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2014/7/7.
 */
@Service
public class ActivityInfoService extends BaseGmService {

    @Resource
    private ActivityInfoDao activityInfoDao;

    @Resource
    private ActivityParameterDao parameterDao;

    @Resource
    private ActivityParameterListDao parameterListDao;

    public List<ActivityInfo> findAllList(ActivityCondition condition) {
        return activityInfoDao.findAllList(condition);
    }

    /**
     * 删除activityInfo，同时删除parameter
     */
    public boolean deleteActivityInfo(Integer activityId) {
        //先获取参数
        List<ActivityParameter> parameterList = parameterDao.findParameterByActivityId(activityId);
        List<ActivityParameter> colParamList = Lists.newArrayList();
        // 选取集合参数类型
        for(ActivityParameter param : parameterList){
            if(param.getType().equals(ParameterType.Collection)){
                colParamList.add(param);
            }
        }

        boolean ret = activityInfoDao.deleteActivityInfo(activityId) && parameterDao.deleteParameters(activityId);
        if(ret && colParamList.size() > 0)
            return parameterListDao.deleteParameterValue(colParamList);
        else
            return ret;
    }

    /**
     * 修改activityInfo
     * @param activityInfo
     * @return
     */
    public boolean updateActivityInfo(ActivityInfo activityInfo) {
        return activityInfoDao.updateActivityInfo(activityInfo);
    }

    /**
     * 查找activityInfo
     * @param activityInfo
     * @return
     */
    public ActivityInfo findActivityInfo(ActivityInfo activityInfo) {
        ActivityInfo activityInfoRet = activityInfoDao.findActivityInfo(activityInfo);
        return activityInfoRet;
    }

    /**
     * 添加activityInfo
     * @param activityInfo
     * @return
     */
    public boolean addActivityInfo(ActivityInfo activityInfo) {
        int ret = activityInfoDao.addActivityInfo(activityInfo);
        if( ret != 0){
            return true;
        }
        return false;
    }

}
