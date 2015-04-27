package com.mokylin.gm.modules.system.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mokylin.gm.config.Global;
import com.mokylin.gm.modules.system.entity.*;
import com.mokylin.gm.modules.system.model.Pms_config;
import com.mokylin.gm.modules.system.service.*;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.modules.system.web.LoginController;
import com.mokylin.gm.servlet.ValidateCodeServlet;
import org.apache.commons.lang3.StringUtils;
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

    private static final String HASH_ALGORITHM = "MD5";

    private static final int HASH_INTERATIONS = 1;

    @Resource
    RoleMgrService roleMgrService;
    @Resource
    SysMgrService sysMgrService;
    @Resource
    UserMgrService userMgrService;
    @Resource
    PmsItemMgrService pmsItemMgrService;
    @Resource
    PmsConfigService roleConfigService;
    @Resource
    UserService userService;
    @Resource
    GameMgrService gameMgrService;
    @Resource
    GameParamMgrService gameParamMgrService;

    @Resource
    private MenuService menuService;

    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        if (LoginController.isValidateCodeLogin(token.getUsername(), false, false)) {
            // 判断验证码
            Session session = SecurityUtils.getSubject().getSession();
            String code = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
            if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)) {
                throw new CaptchaException("验证码错误.");
            }
        }

        User user = userService.getUserByUserName(token.getUsername());
        if (user != null) {
            AuthenticationInfo info = new SimpleAuthenticationInfo(new Principal(user), user.getPassword(), getName());
            return info;
        } else {
            return null;
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Principal principal = (Principal) getAvailablePrincipal(principals);
        User user = userService.getUserByUserName(principal.getUserName());
        if (user != null) {

            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            if(user.isAdmin()){
                //如果是管理员的话 查询所有的权限项
                List<Pms_item> pmsItems = pmsItemMgrService.listBySysId(null, 1);
                for(Pms_item item:pmsItems){
                    if(item.getPms_item_name()!=null&&!item.getPms_item_name().trim().equals("")){
                        info.addStringPermission(item.getPms_item_name());
                        user.getPermissions().put(item.getPms_item_name(),new Pms_config(item.getPms_item_name(),item.getConfigValue(),item.getPms_item_type()));
                    }
                }
            }else{
                List<Pms_config> items=getPms(Integer.parseInt(Global.getConfig("sysid")),user.getId());
                for(Pms_config config:items){
                    info.addStringPermission(config.getName());
                    user.getPermissions().put(config.getName(),config);
                }
            }
            UserUtils.putCache(UserService.CACHE_USER, user);
            // 更新登录IP和时间
            userService.updateUserLoginInfo(user.getId());
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

    /**
     * 授权用户信息
     */
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
                cacheMap = new HashMap<String, Object>();
            }
            return cacheMap;
        }

    }
    private List<Pms_config> getPms(int sysId,long userId){
        //验证成功
        //获取所有的权限项
        Map<String,Pms_config> configMap= Maps.newHashMap();
        Map<Integer,Pms_item> pmsItemMap = pmsItemMgrService.mapPmsItem(sysId);

        //混合用户所有的角色权限
        List<Role> roles=roleConfigService.findAllRole(userId,sysId);
        for (Role role:roles) {
            List<Role_config> configs=roleConfigService.findConfigByRoleId(role.getId());
            for (Role_config config:configs) {
                Pms_item item=pmsItemMap.get(config.getConfig_item_id());
                if(config.getType()==1&&item!=null){//开关类型
                    Pms_config pms_config=configMap.get(item.getPms_item_name());
                    if(pms_config==null){
                        pms_config=new Pms_config(item.getPms_item_name(),config.getConfig_item_value(),1);
                        configMap.put(item.getPms_item_name(),pms_config);
                    }
                }else if(config.getType()==2&&item!=null) {//范围类型 需要混合值
                    Pms_config pms_config=configMap.get(item.getPms_item_name());
                    if(pms_config==null){
                        pms_config=new Pms_config(item.getPms_item_name(),config.getConfig_item_value(),2);
                        configMap.put(item.getPms_item_name(),pms_config);
                    }else{
                        pms_config.setValue(combineString(pms_config.getValue(),config.getConfig_item_value()));
                        configMap.put(item.getPms_item_name(),pms_config);
                    }
                }
            }
        }
        //混合用户的权限
        List<User_config> userConfigs=userMgrService.findConfigByUserId(userId);
        for(User_config config:userConfigs){
            Pms_item item=pmsItemMap.get(config.getConfig_item_id());
            if(config.getType()==1&&item!=null){//开关类型
                Pms_config pms_config=configMap.get(item.getPms_item_name());
                if(pms_config==null){
                    pms_config=new Pms_config(item.getPms_item_name(),config.getConfig_item_value(),1);
                    configMap.put(item.getPms_item_name(),pms_config);
                }
            }else if(config.getType()==2&&item!=null) {//范围类型 需要混合值
                Pms_config pms_config=configMap.get(item.getPms_item_name());
                if(pms_config==null){
                    pms_config=new Pms_config(item.getPms_item_name(),config.getConfig_item_value(),2);
                    configMap.put(item.getPms_item_name(),pms_config);
                }else{
                    pms_config.setValue(combineString(pms_config.getValue(),config.getConfig_item_value()));
                    configMap.put(item.getPms_item_name(),pms_config);
                }
            }
        }
        List<Pms_config> configList= Lists.newArrayList();
        for (Pms_config config : configMap.values()) {
            configList.add(config);
        }
        return  configList;
    }
    private String combineString(String s1,String s2){
        Set<String> set= Sets.newHashSet();
        String result="";
        list2set(set, Arrays.asList(s1.split(",")));
        list2set(set,Arrays.asList(s2.split(",")));
        for (String str : set) {
            result+=str+",";
        }
        return result;
    }
    private void list2set( Set set,List list){
        for (int i = 0; i < list.size(); i++) {
            set.add(list.get(i));
        }
    }
}
