package com.mokylin.gm.modules.system.security;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.mokylin.gm.entity.Permission;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.service.UserService;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.modules.system.web.LoginController;
import com.mokylin.gm.security.Digests;
import com.mokylin.gm.servlet.ValidateCodeServlet;
import com.mokylin.gm.utils.BdpSpi;
import com.mokylin.gm.utils.Encodes;
import com.mokylin.gm.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * 系统安全认证实现类
 *
 * @author yongbo.chen
 * @version 2014-6-10
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {  

    @Resource
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
       
    	UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        String encodePassWord = Encodes.encodeHex(Digests.md5(String.valueOf(token.getPassword()).getBytes()));
 
        String username = token.getUsername();
        
        int check = userService.CheckUser(username);
        
        if (check != 0)
        {
        	 User user = userService.getUserByUserName(username);
             

             if(user != null && encodePassWord.equalsIgnoreCase(user.getPassword())) 
             {
                 Principal userPrincipal = new Principal(user.getUserName());
                 userPrincipal.getCacheMap().put(UserService.CACHE_USER, user);
                 AuthenticationInfo info = new SimpleAuthenticationInfo(userPrincipal, encodePassWord, getName());
                 return info;
             }
             else
                 return null;
        }
        else
        {
        	return null;
        }
       
    }
    

    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;
        private String userName;
        private String name;
        private Map<String, Object> cacheMap;

        public Principal(User user) {
            this.id = user.getId();
            this.userName = user.getUserName();
            this.name = user.getName();
        }

        public Principal(String userName) {
            this.userName = userName;
        }

        public Long getId() {
            return id;
        }

        public String getUserName() {
            return userName;
        }

        public String getName() {
            return name;
        }

        public Map<String, Object> getCacheMap() {
            if (cacheMap == null) {
                cacheMap = Maps.newHashMap();
            }
            return cacheMap;
        }

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemAuthorizingRealm.class);

    private static final String HASH_ALGORITHM = "MD5";

    private static final int HASH_INTERATIONS = 1;
    

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Principal principal = (Principal) getAvailablePrincipal(principals);
        User user = UserUtils.getUser();
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            Set<String> permissionSet = user.getPermissions().keySet();
            Iterator<String> it = permissionSet.iterator();
            while (it.hasNext()) {
                Permission permission = user.getPermissions().get(it.next());
                if("1".equals(permission.getType()) && "1".equals(permission.getValue()))
                    info.addStringPermission(permission.getName());
            }
            // 记录登录IP和时间
            userService.updateUserLoginInfo(user.getUserName());
            return info;
        } else {
            return null;
        }
    }

    /**
     * 设定密码校验的Hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
        matcher.setHashIterations(HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    /**
     * 清空用户关联权限认证，待下次使用时重新加载
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清空所有关联认证,即清除所有用户授权信息缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }
}
