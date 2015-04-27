package com.mokylin.gm.modules.system.service;

import com.mokylin.gm.modules.system.dao.RoleConfigDao;
import com.mokylin.gm.modules.system.dao.RoleDao;
import com.mokylin.gm.modules.system.entity.Role;
import com.mokylin.gm.modules.system.entity.User;
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
public class RoleMgrService extends BaseGmService {
    @Resource
    RoleDao dao;
    public Page<Role> pageList(Page<Role> page, Role role) {
        return dao.pageList(page,role);
    }
    public Role findById(int roleId){
        return dao.findById(roleId);
    }

    @Transactional
    public  boolean addUsers(List<User> users,int roleId){
        return dao.addUsers(users, roleId);
    }

    public boolean delUser(int userId, int roleId) {
        return dao.delUser(userId,roleId);
    }

    public List<Role> listBySysId(int sysId) {
        return dao.listBySysId(sysId);
    }

    public boolean addRole(String roleName,int sysId) {
        return dao.addRole(roleName,sysId);
    }

    public boolean delRole(int roleId) {
        return dao.delRole(roleId);
    }

//    public boolean addConfig(int roleId, String pmsItemIds) {
//        return dao.addConfig( roleId,  pmsItemIds);
//    }


}
