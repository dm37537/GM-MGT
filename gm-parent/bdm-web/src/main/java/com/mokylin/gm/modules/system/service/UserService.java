package com.mokylin.gm.modules.system.service;

import com.mokylin.gm.modules.system.dao.UserDao;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.security.SystemAuthorizingRealm;
import com.mokylin.gm.modules.system.security.SystemAuthorizingRealm.Principal;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.security.Digests;
import com.mokylin.gm.service.BaseGmService;
import com.mokylin.gm.utils.Encodes;
import com.mokylin.gm.utils.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户 Service
 * Created by yongbo.chen
 * Time:2014/6/13 16:07
 */
@Service
public class UserService extends BaseGmService {

    public static final String CACHE_USER = "user";

    @Resource
    private UserDao userDao;

    @Resource
    SystemAuthorizingRealm realm;

    /**
     * 获取登录用户
     *
     * @return
     */
    public User getUser() {
        // 获取shiro缓存
        User user = (User) UserUtils.getCache(CACHE_USER);
        if (user == null) {
            try {
                Subject subject = SecurityUtils.getSubject();
                Principal principal = (Principal) subject.getPrincipal();
                if (principal != null) {
                    user = userDao.findByUserId(principal.getId());
                    UserUtils.putCache(CACHE_USER, user);
                }
            } catch (Exception e) {
                logger.error("get user exception:{}", e.getMessage(), e);
            }
        }
        if (user == null) {
            user = new User();
        }
//        if (user == null) {
//            user = new User();
//            try {
//                // 登出
//                SecurityUtils.getSubject().logout();
//            } catch (Exception e) {
//                logger.error("logout exception:{}", e.getMessage(), e);
//            }
//        }

        return user;
    }

    public User getUser(boolean isRefresh) {
        if (isRefresh) {
            UserUtils.removeCache(CACHE_USER);
        }
        return getUser();
    }

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    public User getUserByUserName(String username) {
        return userDao.findByUserName(username);
    }

    /**
     * 根据用户ID获取用户
     *
     * @param id
     * @return
     */
    public User getUserById(long id) {
        return userDao.findByUserId(id);
    }
    /**
     * 根据用户ID获取用户
     *
     * @param id
     * @return
     */
    public User getUserById4All(long id) {
        return userDao.findByUserId4All(id);
    }
    /**
     * 更新用户登录信息，IP和登录时间
     * @param id
     */
    public void updateUserLoginInfo(long id) {
        userDao.updateUserLoginInfo(SecurityUtils.getSubject().getSession().getHost(), id);
    }

    /**
     * 更新用户个人信息
     * @param user
     * @return
     */
    public boolean updateUser(User user) {
        boolean ret = userDao.update(user);
        realm.clearAllCachedAuthorizationInfo();
        return ret;
    }

    /**
     * 修改密码
     * @param id
     * @param userName
     * @param newPassword
     * @return
     */
    public boolean updatePasswordById(long id, String userName, String newPassword) {
        byte[] newPasswordEncrypt = Digests.md5(newPassword.getBytes());
        boolean ret = userDao.updatePasswordById(id, Encodes.encodeHex(newPasswordEncrypt));
        realm.clearCachedAuthorizationInfo(userName);
        return ret;
    }
    /**
     * 修改密码 密码已加密
     * @param id
     * @param userName
     * @param newPasswordEncoded 已经加密过的密码
     * @return
     */
    public boolean updatePasswordByIdEncoded(long id, String userName, String newPasswordEncoded) {
        boolean ret = userDao.updatePasswordById(id, newPasswordEncoded);
        realm.clearCachedAuthorizationInfo(userName);
        return ret;
    }
    /**
     * 验证密码
     * @param plainPassword 明文密码
     * @param password 密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        byte[] hasPassword = Digests.md5(plainPassword.getBytes());
        return password.equalsIgnoreCase(Encodes.encodeHex(hasPassword));
    }
    
	
    
}
