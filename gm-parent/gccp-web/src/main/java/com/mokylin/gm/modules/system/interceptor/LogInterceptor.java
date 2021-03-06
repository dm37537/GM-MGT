package com.mokylin.gm.modules.system.interceptor;

import com.mokylin.gm.modules.system.dao.LogDao;
import com.mokylin.gm.modules.system.entity.log.Log;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.service.base.BaseGmService;
import com.mokylin.gm.utils.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 系统拦截器
 *
 * @author yongbo.chen
 * @version 2014-6-10
 */
public class LogInterceptor extends BaseGmService implements HandlerInterceptor {

    @Resource
    private LogDao logDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//		if(modelAndView!=null) {
//			String viewName = modelAndView.getViewName();
//			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//			if(viewName.startsWith("modules/") && DeviceType.MOBILE.equals(userAgent.getOperatingSystem().getDeviceType())){
//				modelAndView.setViewName(viewName.replaceFirst("modules", "mobile"));
//			}
//		}
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

		String requestRri = request.getRequestURI();
		String uriPrefix = request.getContextPath() + "/";

		if ((StringUtils.startsWith(requestRri, uriPrefix) && (StringUtils.endsWith(requestRri, "/save")
				|| StringUtils.endsWith(requestRri, "/delete") || StringUtils.endsWith(requestRri, "/import")
				|| StringUtils.endsWith(requestRri, "/updateSort") || StringUtils.endsWith(requestRri, "/add"))) || ex!=null){

			User user = UserUtils.getUser();
            if (user!=null && user.getId() != 0){

				StringBuilder params = new StringBuilder();
				int index = 0;
				for (Object param : request.getParameterMap().keySet()){
					params.append((index++ == 0 ? "" : "&") + param + "=");
					params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase((String)param, "password") ? "" : request.getParameter((String)param), 2000));
				}

				Log log = new Log();
				log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
				log.setCreateBy(user.getUserName());
				log.setCreateDate(new Date());
				log.setRemoteAddr(StringUtils.getRemoteAddr(request));
				log.setUserAgent(request.getHeader("user-agent"));
				log.setRequestUri(request.getRequestURI());
				log.setMethod(request.getMethod());
				log.setParams(params.toString());
				log.setException(ex != null ? ex.toString() : "");
				logDao.save(log);

                logger.info("save log {type: {}, loginName: {}, uri: {}}, ", new Object[]{log.getType(), user.getUserName(), log.getRequestUri()});
			}
		}

//		logger.debug("最大内存: {}, 已分配内存: {}, 已分配内存中的剩余空间: {}, 最大可用内存: {}",
//				Runtime.getRuntime().maxMemory(), Runtime.getRuntime().totalMemory(), Runtime.getRuntime().freeMemory(),
//				Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory());

    }

}
