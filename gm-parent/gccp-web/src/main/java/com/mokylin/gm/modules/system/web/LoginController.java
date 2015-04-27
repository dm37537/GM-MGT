package com.mokylin.gm.modules.system.web;

import com.google.common.collect.Maps;
import com.mokylin.gm.config.Global;
import com.mokylin.gm.modules.system.dao.UserDao;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.security.UsernamePasswordToken;
import com.mokylin.gm.modules.system.service.UserService;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.utils.*;
import com.mokylin.gm.web.BaseController;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

/**
 * Created by yongbo.chen
 * Time:2014/6/11 22:24


public class LoginController extends BaseController{
	
	private UserService us;
	
	@RequestMapping(value = "/login2", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
	   User user = us.getUser();
	   Authentication au = new Authentication();
	   UsernamePasswordToken atk = (UsernamePasswordToken) au.createToken(request, response);
	   if (user.getPassword().toCharArray().equals(atk.getPassword()) && user.getUserName().equals(atk.getUsername()))
	   {
		   return "modules/system/sysIndex";
	   }

	return "modules/system/sysLogin";
		
	}
	
}
 */



@Controller
public class LoginController extends BaseController {

    @Resource
    private UserService userService;
    private UserDao userDao;
//    private UserService2 userService2;

    /**
     * 首页，管理登录
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = userService.getUser();
        // 如果已经登录,则跳转到管理首页
        if (user != null)
            return "redirect:" + Global.getAdminPath();

        return "modules/system/sysLogin";
    } 
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(HttpServletRequest request, HttpServletResponse response, Model model) {
       System.out.println(" Register! ");

        return "modules/system/sysLogin";
    } 
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register_2(HttpServletRequest request, HttpServletResponse response, Model model) {
       System.out.println(" Register! ");
       String username = request.getParameter("username");
       String password = request.getParameter("password");
       String name = request.getParameter("name");
       String email = request.getParameter("email");
       String mobile = request.getParameter("mobile");
      
       userService.CreateNewUser(username, password, name, email, mobile);
      
       /*
       System.out.println(username + " POST ");
	   System.out.println(password + " POST ");
	   System.out.println(name + " POST ");
	   System.out.println(email + " POST ");
	   System.out.println(mobile + " POST ");
		*/
        return "modules/system/sysLogin";
    } 
    /*
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
    	
    	if(request.getParameter("username") != null)
    	{
    		User user = userService2.getUserByUserName(request.getParameter("username"));
    		System.out.println(request.getParameter("username") + "GET");
    		System.out.println(request.getParameter("password") + "GET");
    		 if (user != null)
        	 {
        		 System.out.println(user.getUserName());
        	 }
    		 // if (user != null)
    	      //      return "redirect:" + Global.getAdminPath();
    	}
    	
    	      
       User u = new User();
       u.setUserName(username);
       u.setPassword(password);
       u.setName(name);
       u.setEmail(email);
       u.setMobile(mobile);
    	User user = userService.getUser();
        // 如果已经登录,则跳转到管理首页
        if (user != null)
            return "redirect:" + Global.getAdminPath();

   	  
        // 如果已经登录,则跳转到管理首页
      
    	System.out.println("Default");
        return "modules/system/sysLogin";
    }
    
    
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login_p(HttpServletRequest request, HttpServletResponse response, Model model) {
       String type_in = request.getParameter("username");
       System.out.println(type_in);
       User user = UserUtils.getUser();
       if (user != null)
       {
    	   if (request.getParameter("username").equals("meng") || true)
    	   {
    		   System.out.println(request.getParameter("username"));
    		   return null;
    	   }
       }

		   return "modules/system/login";
	
	}
	*/
    /**
     * 登录失败，真正登录的POST请求有Filter完成
    */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();
        if (user != null) {
            return "redirect:" + Global.getAdminPath();
        }
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
        return "modules/system/sysLogin";
    }
 
    /**
     * 登录成功，进入管理首页
     */
    @RequiresUser
    @RequestMapping(value = "/")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtils.getUser();
        // 未登录，则跳转到登录页
        if (user == null) {
            return "redirect:" + Global.getAdminPath() + "/login";
        }

        // 登录成功，验证码计算器清零
        isValidateCodeLogin(user.getUserName(), false, true);

        // 登录成功后，获取上次登录的当前站点ID
        return "modules/system/sysIndex";
    }

    /**
     * 获取主题方案，设置cookie方式
     */
    @RequestMapping(value = "/theme/{theme}")
    public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(theme)) {
            CookieUtils.setCookie(response, "theme", theme);
        } else {
            theme = CookieUtils.getCookie(request, "theme");
        }

        return "redirect:" + request.getParameter("url");
    }

    /**
     * 获取国际化语言，设置cookie方式
     */
    @RequestMapping(value = "/language/{language}")
    public String getLanguage(@PathVariable String language, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isNotBlank(language)) {
            CookieUtils.setCookie(response, "language", language);
        } else {
            language = CookieUtils.getCookie(request, "language");
        }
        request.getSession().setAttribute(ResourceManager.RESOURCE_LANGUAGE, language);
        return "redirect:" + request.getParameter("url");
    }


    /**
     * 是否验证码登录
     *
     * @param userName 用户名
     * @param isFail   计数器加1
     * @param clean    计数清零
     * @return 是否验证码登录
     */
    public static boolean isValidateCodeLogin(String userName, boolean isFail, boolean clean) {
        Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
        if (loginFailMap == null) {
            loginFailMap = Maps.newHashMap();
            CacheUtils.put("loginFailMap", loginFailMap);
        }
        Integer loginFailNum = loginFailMap.get(userName);
        if (loginFailNum == null) {
            loginFailNum = 0;
        }
        if (isFail) {
            loginFailNum++;
            loginFailMap.put(userName, loginFailNum);
        }
        if (clean) {
            loginFailMap.remove(userName);
        }
        return loginFailNum >= 3;

    }
}
