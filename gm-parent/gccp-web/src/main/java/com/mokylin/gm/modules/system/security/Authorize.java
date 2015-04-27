/*package com.mokylin.gm.modules.system.security;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.mokylin.gm.entity.Permission;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.security.SystemAuthorizingRealm.Principal;
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

public class Authorize extends AuthorizingRealm {
	
	 @Resource
	 private UserService userService;

	protected AuthorizationInfo doGetAuthorizationInfo(AuthenticationToken authcToken) throws AuthenticationException 
	{
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 登录验证
        String encodePassWord = Encodes.encodeHex(Digests.md5(String.valueOf(token.getPassword()).getBytes()));
        
        String username = token.getUsername();
        
        User user = userService.getUserByUserName(username);

        if(user != null) {

            Principal userPrincipal = new Principal(user.getUserName());
            userPrincipal.getCacheMap().put(UserService.CACHE_USER, user);
            AuthenticationInfo info = new SimpleAuthenticationInfo(userPrincipal, encodePassWord, getName());
            return (AuthorizationInfo) info;
        }
        else
            return null;
		return null;
	}
	




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



	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
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
}*/
