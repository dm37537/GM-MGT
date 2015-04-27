package com.mokylin.gm.modules.system.service;

import com.google.common.collect.Lists;
import com.mokylin.gm.modules.system.dao.action.ActionDao;
import com.mokylin.gm.modules.system.dao.action.ActionParameterDao;
import com.mokylin.gm.modules.system.dao.action.ActionParameterListDao;
import com.mokylin.gm.modules.system.entity.ParameterType;
import com.mokylin.gm.modules.system.entity.action.Action;
import com.mokylin.gm.modules.system.entity.action.ActionCondition;
import com.mokylin.gm.modules.system.entity.action.Parameter;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.service.base.BaseGmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2014/7/1.
 */
@Service
public class ActionService extends BaseGmService{

    @Resource
    private ActionDao actionDao;

    @Resource
    private ActionParameterDao parameterDao;

    @Resource
    private ActionParameterListDao parameterListDao;

    public List<Action> findAllList(ActionCondition condition) {
        return actionDao.findAllList(condition);
    }

    /**
     * 添加action, 添加parameter
     * @param action
     * @return
     */
    public boolean addAction(Action action) {
        int ret = actionDao.addAction(action, UserUtils.getUser().getUserName()) ;
        if(ret != 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 删除action, 删除parameter
     * @param actionId
     * @return
     */
    public boolean deleteAction(Integer actionId) {
        //先获取参数
        List<Parameter> parameterList = parameterDao.findParametersByActionId(actionId);
        List<Parameter> colParamList = Lists.newArrayList();
        // 选取集合参数类型
        for (Parameter param : parameterList) {
            if(param.getType().equals(ParameterType.Collection)) {
                colParamList.add(param);
            }
        }

        boolean ret = actionDao.deleteAction(actionId) && parameterDao.deleteParameters(actionId);
        if(ret && colParamList.size() >0 )
            return parameterListDao.deleteParameterValue(colParamList);
        else
            return ret;
    }

    /**
     * 修改action,先删除parameter，再添加
     * @param action
     * @return
     */
    public boolean updateAction(Action action) {
        return actionDao.updateAction(action);
    }

    /**
     * 根据actionId，查找action及parameter
     * @param action
     * @return
     */
    public Action findAction(Action action) {
        Action actionRet = actionDao.findAction(action);
        return actionRet;
    }
}
