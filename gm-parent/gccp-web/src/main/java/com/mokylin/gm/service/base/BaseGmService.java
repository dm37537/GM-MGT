package com.mokylin.gm.service.base;

import com.google.common.collect.Lists;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.service.BaseService;
import com.mokylin.gm.utils.StringUtils;

import java.util.List;

/**
 * Created by yongbo.chen
 * Time:2014/6/13 16:20
 */
public class BaseGmService extends BaseService {

    /**
     * 数据范围过滤
     *
     * @param user
     * @return
     */
    protected static boolean dataScopeFilter(User user) {
        // 进行权限过滤,多个角色权限范围之间为或者关系
        List<String> dataScope = Lists.newArrayList();

        //超级管理员,跳过权限过滤
//        if (!user.isAdmin()) {
//            //TODO 待完善
//            return false;
//        }
        return true;
    }

    protected List<Long> getIdList(String ids) {
        List<Long> idList = Lists.newArrayList();
        if (StringUtils.isNotBlank(ids)) {
            ids = ids.trim().replace(" ", ",").replace("，", "");
            String[] arrId = ids.split(",");
            for (String id : arrId) {
                if (id.matches("\\d*")) {
                    idList.add(Long.valueOf(id));
                }
            }
        }
        return idList;
    }

}
