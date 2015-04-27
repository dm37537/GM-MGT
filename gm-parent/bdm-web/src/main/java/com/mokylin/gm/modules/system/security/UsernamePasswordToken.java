package com.mokylin.gm.modules.system.security;

/**
 * 用户和密码（包含验证码）令牌类
 *
 * @author yongbo.chen
 * @version 2014-6-10
 */
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

    private static final long serialVersionUID = 1L;

    public UsernamePasswordToken() {
        super();
    }

    public UsernamePasswordToken(String username, char[] password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }

}