package com.mokylin.gm.modules.system.service;

import com.mokylin.gm.modules.system.dao.UserDao2;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.service.base.BaseGmService;

public class UserService2  extends BaseGmService {
	
	private static final String CACHE_USER = "user";
	private UserDao2 userDao2;
	
    public User getUser() {
        User user = (User) UserUtils.getCache(CACHE_USER);
        return user;
    }

    public User getUser(boolean isRefresh) {
        if (isRefresh) {
            UserUtils.removeCache(CACHE_USER);
        }
        return getUser();
    }

    public User getUserByUserName(String username) {
        return userDao2.FindByUsername(username);
    }
	

}
