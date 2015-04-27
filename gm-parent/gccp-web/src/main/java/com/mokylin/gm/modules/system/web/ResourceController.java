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
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequiresUser
@RequestMapping(value = "/system/resource")
public class ResourceController extends BaseController{

    @Resource
    private ResourceMessageService resourceMessageService;

    @RequiresPermissions(value = "resourcemanage")
    @RequestMapping(value = {"list",""})
    public String list(ResourceMessage resourceMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ResourceMessage> page = resourceMessageService.findResourceMessage(new Page<ResourceMessage>(request, response), resourceMessage);
        model.addAttribute("page", page);
        return "modules/system/resource/resourceList";
    }

    /**
     * 跳转添加页面
     * @param resource
     * @param model
     * @return
     */
    @RequiresUser
    @RequestMapping(value = "addForm")
    public String addForm(@ModelAttribute(value = "resource") ResourceMessage resource,Model model){
        return "modules/system/resource/addForm";
    }

    /**
     * 添加
     * @param resource
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequiresPermissions(value = "resource_add")
    @RequestMapping(value = "add")
    public String add(@ModelAttribute(value = "resource")ResourceMessage resource, RedirectAttributes redirectAttributes, Model model) {
        if(resourceMessageService.addResourceMessage(resource)){
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common","common.save.success")));
            return "redirect:" + Global.getAdminPath() +"/system/resource/?repage";
        }
        else {
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common","common.save.failed")));
            redirectAttributes.addFlashAttribute("resource", resource);
            return "redirect:" + Global.getAdminPath() + "/system/resource/addForm";
        }
    }

    @RequiresUser
    @RequestMapping(value = "updateForm")
    public String updateForm(@ModelAttribute(value = "resource") ResourceMessage resource, Model model){
        resource = resourceMessageService.findResourceMessage(resource);
        model.addAttribute("resource", resource);
        return "modules/system/resource/updateForm";
    }

    @RequiresPermissions(value = "resource_update")
    @RequestMapping(value = "update")
    public String update(@ModelAttribute(value = "resource") ResourceMessage resource, RedirectAttributes redirectAttributes, Model model) {
        if(resourceMessageService.updateResourceMessage(resource)) {
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common","common.save.success")));
            return "redirect:" + Global.getAdminPath() +"/system/resource/list";
        }
        else {
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common","common.save.failed")));
            redirectAttributes.addFlashAttribute("resource", resource);
            return "redirect:" + Global.getAdminPath() + "/system/resource/updateForm";
        }
    }

    /**
     * 删除
     * @param locale
     * @param bundle
     * @param key
     * @param redirectAttributes
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions(value = "resource_delete")
    @RequestMapping(value = "delete")
    public String delete(String locale, String bundle, String key, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response){
        if(resourceMessageService.deleteResourceMessage(locale, bundle, key))
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString(request, "common", "common.delete.success")));
        else
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString(request, "common", "common.delete.failed")));
        return "redirect:" + Global.getAdminPath() + "/system/resource/?repage";
    }
}
