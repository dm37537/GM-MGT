package com.mokylin.gm.modules.system.web;

import com.mokylin.gm.modules.system.entity.log.Log;
import com.mokylin.gm.modules.system.entity.log.LogCondition;
import com.mokylin.gm.modules.system.service.LogService;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志 Controller
 * Created by Administrator on 2014/6/30.
 */
@Controller
@RequiresUser
@RequestMapping( value = "/system/log")
public class LogController extends BaseController{

    @Resource
    private LogService logService;

    @RequiresPermissions(value = "loggerquery")
    @RequestMapping(value = {"list", ""})
    public String list(@ModelAttribute(value = "logCondition") LogCondition logCondition, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Log> page = logService.query(logCondition, new Page<Log>(request, response));
        model.addAttribute("page", page);
        return "modules/system/log/logList";
    }
}
