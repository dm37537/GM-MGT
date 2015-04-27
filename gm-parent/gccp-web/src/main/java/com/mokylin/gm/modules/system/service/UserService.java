package com.mokylin.gm.modules.system.service;

import com.mokylin.gm.modules.system.dao.UserDao;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.security.SystemAuthorizingRealm;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.security.Digests;
import com.mokylin.gm.service.base.BaseGmService;
import com.mokylin.gm.utils.BdpSpi;
import com.mokylin.gm.utils.Encodes;
import org.apache.shiro.SecurityUtils;
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
     * 记录用户登录信息，IP和登录时间
     * @param userName
     */
    public void updateUserLoginInfo(String userName) {
        userDao.saveUserLoginInfo(SecurityUtils.getSubject().getSession().getHost(), userName);
    }
    
    
   

    /**
     * 更新用户个人信息
     * @param user
     * @return
     */
    public boolean saveUser(User user) {
        boolean ret = userDao.save(user);
        realm.clearAllCachedAuthorizationInfo();
        return ret;
    }

    /**
     * 修改密码
     * @param userName
     * @param newPassword
     * @param oldPassword
     * @return
     */
    public boolean updatePassword(String userName, String newPassword, String oldPassword) {
        String newPassWordEncrypt = Encodes.encodeHex(Digests.md5(newPassword.getBytes()));
        String oldPasswordEncrypt = Encodes.encodeHex(Digests.md5(oldPassword.getBytes()));
        if("true".equals(new BdpSpi().modifyPassword(userName, newPassWordEncrypt, oldPasswordEncrypt))){
            realm.clearCachedAuthorizationInfo(userName);
            return true;
        }
        return false;
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
    
    
    public void CreateNewUser(String userName,String passWord,  String Name,  String Email,  String Mobile)
    {
		String encodePassWord = Encodes.encodeHex(Digests.md5(passWord.getBytes()));
    	
		userDao.CreateNewUser(userName, encodePassWord,  Name,  Email, Mobile);
    }
    
    public int CheckUser(String username)
    {
    	
    	return userDao.CheckUser(username);
    	
    }
    
}
