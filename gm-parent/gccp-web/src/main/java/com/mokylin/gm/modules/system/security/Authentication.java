/**
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Authentication extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
	
	private String captchaParam = "validateCode";

	protected AuthenticationToken createToken(HttpServletRequest request, HttpServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        if (password == null) {
            password = "";
        }
        boolean rememberMe = true;
        String host = getHost(request);
        String captcha = WebUtils.getCleanParam(request, captchaParam);
        return new UsernamePasswordToken(username, password.toCharArray(), true, null, null);
    }
}
*/
