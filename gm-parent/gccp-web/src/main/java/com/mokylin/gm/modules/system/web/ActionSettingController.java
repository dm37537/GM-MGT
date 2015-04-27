package com.mokylin.gm.modules.system.web;

import com.mokylin.gm.config.Global;
import com.mokylin.gm.entity.MessageType;
import com.mokylin.gm.entity.ResultMsg;
import com.mokylin.gm.modules.system.entity.action.Action;
import com.mokylin.gm.modules.system.entity.action.ActionCondition;
import com.mokylin.gm.modules.system.entity.action.Parameter;
import com.mokylin.gm.modules.system.service.ActionParameterListService;
import com.mokylin.gm.modules.system.service.ActionParameterService;
import com.mokylin.gm.modules.system.service.ActionService;
import com.mokylin.gm.utils.ResourceManager;
import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能点配置器 Controller
 * Created by Administrator on 2014/7/1.
 */
@Controller
@RequiresUser
@RequestMapping(value = "/system/actionsetting")
public class ActionSettingController extends BaseController{

    @Resource
    private ActionService actionService;

    @Resource
    private ActionParameterService actionParameterService;

    @Resource
    private ActionParameterListService parameterListService;

    //////////////功能配置管理///////////////
    @RequiresPermissions(value = "actionsetting")
    @RequestMapping(value = {"list", ""})
    public String list(@ModelAttribute(value = "condition") ActionCondition condition, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("list", actionService.findAllList(condition));
        return "modules/system/actionsetting/actionList";
    }

    /**
     * 跳转添加页面
     * @param action
     * @param model
     * @return
     */
    @RequiresUser
    @RequestMapping( value = "addForm")
    public String addForm(@ModelAttribute(value = "action") Action action, Model model){
        return "modules/system/actionsetting/addForm";
    }

    /**
     * 添加
     * @param action
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequiresPermissions(value = "actionsetting_add")
    @RequestMapping(value = "add")
    public String add(@ModelAttribute(value = "action") Action action, RedirectAttributes redirectAttributes, Model model){
        if(!beanValidator(model, action)){
            return addForm(action, model);
        }
        if(actionService.addAction(action)){
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common","common.save.success")));
            return "redirect:" + Global.getAdminPath() +"/system/actionsetting/list";
        }
        else {
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common","common.save.failed")));
            redirectAttributes.addFlashAttribute("action", action);
            return "redirect:" + Global.getAdminPath() + "/system/actionsetting/addForm";
        }
    }

    /**
     * 跳转修改页面
     * @param action
     * @param model
     * @return
     */
    @RequiresUser
    @RequestMapping(value = "updateForm")
    public String updateForm(@ModelAttribute(value = "action") Action action, Model model) {
        // 查找action
        action = actionService.findAction(action);
        model.addAttribute("action", action);
        return "modules/system/actionsetting/updateForm";
    }

    @RequiresPermissions(value = "actionsetting_update")
    @RequestMapping(value = "update")
    public String update(Action action, RedirectAttributes redirectAttributes, Model model) {
        if(!beanValidator(model, action)){
            return updateForm(action, model);
        }
        if(actionService.updateAction(action)) {
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common","common.save.success")));
            return "redirect:" + Global.getAdminPath() +"/system/actionsetting/list";
        }
        else {
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common","common.save.failed")));
            redirectAttributes.addFlashAttribute("action", action);
            return "redirect:" + Global.getAdminPath() + "/system/actionsetting/updateForm";
        }
    }

    /**
     * 删除功能
     */
    @RequiresPermissions(value = "actionsetting_delete")
    @RequestMapping(value = "delete")
    public String delete(Integer id, RedirectAttributes redirectAttributes, Model model) {
        if(actionService.deleteAction(id)) {
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common","common.delete.success")));
        }
        else{
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common","common.delete.failed")));
        }
        return "redirect:" +Global.getAdminPath()+ "/system/actionsetting/list";
    }

    ///////////////////参数管理///////////////////
    @RequiresPermissions(value = "actionsetting")
    @RequestMapping(value = "listParam")
    public String listParam(@RequestParam int id, @RequestParam String actionName, HttpServletRequest request, Model model) {
        model.addAttribute("list", actionParameterService.findActionParametersByActionId(id));
        model.addAttribute("actionId", id);
        model.addAttribute("actionName", actionName);
        return "modules/system/actionsetting/param/paramList";
    }

    /**
     * 跳转添加参数页面
     */
    @RequiresUser
    @RequestMapping( value = "addParamForm")
    public String addParamForm(@ModelAttribute(value = "parameter")Parameter parameter, @RequestParam int actionId, Model model) {
        parameter.setActionId(actionId);
        return "modules/system/actionsetting/param/addParamForm";
    }

    /**
     * 添加活动配置
     */
    @RequiresPermissions(value = "actionsetting_add")
    @RequestMapping( value = "addParam")
    public String addParam(@ModelAttribute(value = "parameter") Parameter parameter, RedirectAttributes redirectAttributes, Model model) {
        if(!beanValidator(model, parameter)) {
            return addParamForm(parameter, parameter.getId(), model);
        }
        if(actionParameterService.addActionParameter(parameter)) {
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common","common.save.success")));
            redirectAttributes.addFlashAttribute("parameter", new Parameter(parameter.getActionId()));
        }
        else {
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common","common.save.failed")));
            redirectAttributes.addFlashAttribute("parameter", parameter);
        }

        redirectAttributes.addAttribute("actionId", parameter.getActionId());
        return "redirect:" + Global.getAdminPath() + "/system/actionsetting/addParamForm";
    }

    /**
     * 跳转修改参数页面
     */
    @RequiresUser
    @RequestMapping(value = "updateParamForm")
    public String updateParamForm(@ModelAttribute(value = "parameter") Parameter parameter, Model model) {
        // 查找Parameter
        parameter = actionParameterService.findActionParameterByParamId(parameter.getId());
        model.addAttribute("parameter", parameter);
        return "modules/system/actionsetting/param/updateParamForm";
    }

    /**
     * 修改参数
     */
    @RequiresPermissions(value = "actionsetting_update")
    @RequestMapping( value = "updateParam")
    public String updateParam(Parameter parameter, RedirectAttributes redirectAttributes, Model model){
        if(!beanValidator(model, parameter)){
            return updateParamForm(parameter, model);
        }
        if(actionParameterService.updateActionParameter(parameter)) {
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common","common.save.success")));
        }
        else {
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common","common.save.failed")));
        }
        redirectAttributes.addFlashAttribute("parameter", parameter);
        return "redirect:" + Global.getAdminPath() + "/system/actionsetting/updateParamForm";
    }

    /**
     * 删除参数
     */
    @RequiresPermissions(value = "actionsetting_delete")
    @RequestMapping(value = "deleteParam")
    public String deleteParam(int id, String actionName, RedirectAttributes redirectAttributes, Model model) {
        int actionId = actionParameterService.findActionParameterByParamId(id).getActionId();
        if(actionParameterService.deleteActionParameter(id)){
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common", "common.delete.success")));
        }
        else {
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common", "common.delete.failed")));
        }
        return "redirect:" + Global.getAdminPath() + "/system/actionsetting/listParam?id=" + actionId + "&actionName=" + actionName;
    }

    //////////////集合参数值///////////////
    /**
     * 显示集合参数值
     */
    @RequiresUser
    @RequestMapping(value = "listParamValue")
    public String listParamValue(HttpServletRequest request, Model model){
        int paramId = Integer.valueOf(request.getParameter("paramId"));
        Parameter parameter = actionParameterService.findActionParameterByParamId(paramId);
        parameter.setValues(parameterListService.findActionParameterValues(paramId));
        model.addAttribute("parameter", parameter);
        return "modules/system/actionsetting/param/paramValueList";
    }

    @RequiresPermissions(value = "actionsetting_update")
    @RequestMapping(value = "saveParamValue")
    public String saveParamValue(@ModelAttribute Parameter parameter, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {
        if(parameterListService.saveActionParameterValues(parameter))
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common", "common.save.success")));
        else
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common", "common.save.failed")));
        return "redirect:" + Global.getAdminPath() + "/system/actionsetting/listParamValue?paramId=" + parameter.getId();
    }
}
