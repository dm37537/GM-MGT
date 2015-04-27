package com.mokylin.gm.modules.system.interceptor;

import com.mokylin.gm.modules.system.dao.LogDao;
import com.mokylin.gm.modules.system.dao.SysDao;
import com.mokylin.gm.modules.system.entity.Log;
import com.mokylin.gm.modules.system.entity.Sys;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.service.BaseGmService;
import com.mokylin.gm.utils.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;

/**
 * 系统拦截器
 *
 * @author yongbo.chen
 * @version 2014-6-10
 */
public class ApiInterceptor extends BaseGmService implements HandlerInterceptor {

    @Resource
    private SysDao sysDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");//通知response以UTF-8发送
        response.setContentType("text/html;charset=UTF-8");//设置浏览器以UTF-8打开
        String requestRri = request.getRequestURI();
        String uriPrefix = request.getContextPath() + "/";

        StringBuilder params = new StringBuilder();
        int index = 0;
        for (Object param : request.getParameterMap().keySet()){
            params.append((index++ == 0 ? "" : "&") + param + "=");
            params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase((String)param, "password") ? "" : request.getParameter((String)param), 2000));
        }
        if ((StringUtils.startsWith(requestRri, uriPrefix) && (StringUtils.contains(requestRri, "/api/"))))
        {
            String sys_id=request.getParameter("sys_id");
            String verify_code=request.getParameter("verify_code");

            if(sys_id==null||!StringUtils.isNumeric(sys_id)){
                response.getWriter().write("缺少sys_id参数或者sys_id不为数字");
                return false;
            }
            if(verify_code==null){
                response.getWriter().write("缺少verify_code参数");
                return false;
            }
            Sys sys=sysDao.findSysById(Integer.parseInt(sys_id));
            if(sys==null){
                response.getWriter().write("ID为["+sys_id+"]的系统不存在");
                return false;
            }else if(!verify_code.equals(sys.getVerify_code())){
                response.getWriter().write("ID为["+sys_id+"]的系统验证码[verify_code]不匹配");
                return false;
            }
            String ipList=sys.getIp_list();
            if(!ipList.endsWith(",")){
                ipList+=",";
            }
            if(!ipList.startsWith(",")){
                ipList=","+ipList;
            }
            if(!ipList.contains(","+request.getRemoteAddr()+",")){
                response.getWriter().write("ip禁止");
                return false;
            }
            logger.info("接口被调用  {ip: {}, sys_id: {}, uri: {},params:{}} ", new Object[]{request.getRemoteAddr(),sys_id,requestRri,params.toString()});
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {


    }
}