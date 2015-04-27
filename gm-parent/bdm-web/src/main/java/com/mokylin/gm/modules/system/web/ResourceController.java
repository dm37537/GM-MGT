package com.mokylin.gm.modules.system.web;

import com.mokylin.gm.config.Global;
import com.mokylin.gm.entity.MessageType;
import com.mokylin.gm.entity.ResultMsg;
import com.mokylin.gm.modules.system.entity.ResourceMessage;
import com.mokylin.gm.modules.system.service.ResourceMessageService;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.ResourceManager;
import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 国际化资源管理
 * Created by Administrator on 2014/6/23.
 */
@Controller
@RequestMapping(value = "/system/resource")
public class ResourceController extends BaseController{

    @Resource
    private ResourceMessageService resourceMessageService;
    @RequiresPermissions("common.menu.resourcemanage")
    @RequestMapping(value = {"list",""})
    public String list(ResourceMessage resourceMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ResourceMessage> page = resourceMessageService.findResourceMessage(new Page<ResourceMessage>(request, response), resourceMessage);
        model.addAttribute("page", page);
        return "modules/system/resource/resourceList";
    }

    @RequestMapping(value = "delete")
    @RequiresPermissions("common.menu.resourcemanage")
    public String delete(String locale, String bundle, String key, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response){
        if(resourceMessageService.deleteResourceMessage(locale, bundle, key))
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString(request, "common", "common.delete.success")));
        else
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString(request, "common", "common.delete.failed")));
        return "redirect:" + Global.getAdminPath() + "/system/resource/?repage";
    }
}
