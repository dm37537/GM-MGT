package com.mokylin.gm.modules.system.service;

import com.mokylin.gm.modules.system.dao.RoleConfigDao;
import com.mokylin.gm.modules.system.dao.RoleDao;
import com.mokylin.gm.modules.system.dao.UserDao;
import com.mokylin.gm.modules.system.entity.Role;
import com.mokylin.gm.modules.system.entity.Role_config;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.entity.User_config;
import com.mokylin.gm.service.BaseGmService;
import com.mokylin.gm.utils.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Service
public class PmsConfigService extends BaseGmService {
    @Resource
    RoleConfigDao roleConfigDao;
    @Resource
    UserDao userDao;
    @Resource
    RoleDao roleDao;
    public List<Role_config> findConfigByRoleId(final int roleId){
        return  roleConfigDao.findConfigByRoleId(roleId);
    }

    public Role_config findRoleConfig(int role_id, int config_item_id) {
        return roleConfigDao.findConfig(role_id,config_item_id);
    }
    public boolean addRoleConfig(int role_id, int config_item_id, String config_item_value,int type){
        return roleConfigDao.addConfig(role_id, config_item_id, config_item_value,type);
    }
    public boolean addRoleConfigs(int roleId, String pmsItemIds) {
        return roleDao.addConfigs(roleId, pmsItemIds);
    }


    public User_config findUserConfig(int user_id, int config_item_id) {
        return userDao.findConfig(user_id,config_item_id);
    }

    public boolean addUserConfig(int user_id, int config_item_id, String config_item_value, int type,int sysId) {
        return userDao.addConfig(user_id, config_item_id, config_item_value,type,sysId);
    }


    public boolean addUserConfigs(int userId, String pmsItemIds,int sysId) {
        return userDao.addConfigs(userId, pmsItemIds,sysId);
    }

    public List<Role> findAllRole(long userId,int sysId) {
        return roleDao.findAllRole(userId,sysId);
    }
//    public boolean addConfig4RoleTyp2(int role_id, int config_item_id, String config_item_value) {
//        return roleConfigDao.addConfig4RoleTyp2(role_id, config_item_id, config_item_value);
//    }
}
