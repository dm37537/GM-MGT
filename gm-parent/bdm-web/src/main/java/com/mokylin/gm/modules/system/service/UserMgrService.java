package com.mokylin.gm.modules.system.service;

import com.mokylin.gm.modules.system.dao.UserDao;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.entity.User_config;
import com.mokylin.gm.service.BaseGmService;
import com.mokylin.gm.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2014/6/27.
 */
@Service
public class UserMgrService extends BaseGmService {
    @Resource
    private UserDao userDao;
    /**
     *分页获取用户数据
     * @param userPage
     * @param user
     * @return
     */
    public Page<User> pageList(Page<User> userPage, User user) {

        return userDao.pageList(userPage,user);
    }

    public  boolean save(User user) {
        return  userDao.save(user);
    }

    public boolean update(User user) {
        return userDao.update(user);
    }

    public Page<User> findUserByRoleId(Page<User> userPage, User user,int roleId) {
        return userDao.findUserByRoleId(userPage,user,roleId);
    }
    public List<User> findUsersByUserNames(String userNames){
        return userDao.findUsersByUserNames(userNames);
    }

    public boolean updateStatus(long userId, int status) {
        return userDao.updateStatus(userId,status);
    }

    public boolean resetPsw(int userId) {
        return userDao.resetPsw(userId);

    }

    public List<User_config> findConfigByUserId(long useId) {
        return userDao.findConfigByUserId(useId);
    }

}
