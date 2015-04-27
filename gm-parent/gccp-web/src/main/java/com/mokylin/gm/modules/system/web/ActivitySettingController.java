package com.mokylin.gm.modules.system.web;

import com.mokylin.gm.config.Global;
import com.mokylin.gm.entity.MessageType;
import com.mokylin.gm.entity.ResultMsg;
import com.mokylin.gm.modules.system.entity.activity.ActivityCondition;
import com.mokylin.gm.modules.system.entity.activity.ActivityInfo;
import com.mokylin.gm.modules.system.entity.activity.ActivityParameter;
import com.mokylin.gm.modules.system.entity.activity.ActivityParameterValue;
import com.mokylin.gm.modules.system.service.ActivityInfoService;
import com.mokylin.gm.modules.system.service.ActivityParameterService;
import com.mokylin.gm.modules.system.service.ActivityParameterListService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 活动配置器
 * Created by Administrator on 2014/7/1.
 */
@Controller
@RequiresUser
@RequestMapping(value = "/system/activitysetting")
public class ActivitySettingController extends BaseController{

    @Resource
    private ActivityInfoService activityInfoService;

    @Resource
    private ActivityParameterService activityParameterService;

    @Resource
    private ActivityParameterListService parameterListService;

    /////////////////////活动配置管理///////////////////////
    @RequiresPermissions(value = "activitysetting")
    @RequestMapping(value = {"list",""})
    public String list(@ModelAttribute(value = "condition")ActivityCondition condition, Model model){
        model.addAttribute("list", activityInfoService.findAllList(condition));
        return "modules/system/activitysetting/activityInfoList";
    }

    /**
     * 跳转添加活动配置页面
     */
    @RequiresUser
    @RequestMapping( value = "addForm")
    public String addForm(@ModelAttribute(value = "activityInfo")ActivityInfo activityInfo, Model model) {
        return "modules/system/activitysetting/addForm";
    }

    /**
     * 添加活动配置
     */
    @RequiresPermissions(value = "activitysetting_add")
    @RequestMapping( value = "add")
    public String add(@ModelAttribute(value = "activityInfo") ActivityInfo activityInfo, RedirectAttributes redirectAttributes, Model model) {
        if(!beanValidator(model, activityInfo)) {
            return addForm(activityInfo, model);
        }
        if(activityInfoService.addActivityInfo(activityInfo)) {
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common","common.save.success")));
            return "redirect:" + Global.getAdminPath() +"/system/activitysetting/list";
        }
        else {
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common","common.save.failed")));
            redirectAttributes.addFlashAttribute("activityInfo", activityInfo);
            return "redirect:" + Global.getAdminPath() + "/system/activitysetting/addForm";
        }
    }

    /**
     * 跳转修改活动配置页面
     */
    @RequiresUser
    @RequestMapping(value = "updateForm")
    public String updateForm(@ModelAttribute(value = "activityInfo") ActivityInfo activityInfo, Model model) {
        // 查找activityInfo
        activityInfo = activityInfoService.findActivityInfo(activityInfo);
        model.addAttribute("activityInfo", activityInfo);
        return "modules/system/activitysetting/updateForm";
    }

    @RequiresPermissions(value = "activitysetting_update")
    @RequestMapping(value = "update")
    public String update(ActivityInfo activityInfo, RedirectAttributes redirectAttributes, Model model) {
        if(!beanValidator(model, activityInfo)){
            return updateForm(activityInfo, model);
        }
        if(activityInfoService.updateActivityInfo(activityInfo)) {
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common","common.save.success")));
            return "redirect:" + Global.getAdminPath() +"/system/activitysetting/list";
        }
        else {
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common","common.save.failed")));
            redirectAttributes.addFlashAttribute("activityInfo", activityInfo);
            return "redirect:" + Global.getAdminPath() + "/system/activitysetting/updateForm";
        }
    }

    /**
     * 删除活动配置
     */
    @RequiresPermissions(value = "activitysetting_delete")
    @RequestMapping(value = "delete")
    public String delete(Integer id, RedirectAttributes redirectAttributes, Model model) {
        if(activityInfoService.deleteActivityInfo(id)){
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common", "common.delete.success")));
        }
        else{
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common","common.delete.failed")));
        }
        return "redirect:" + Global.getAdminPath()+ "/system/activitysetting/list";
    }

    /////////////////////////参数管理/////////////////////////////
    /**
     * 参数列表
     */
    @RequiresPermissions(value = "activitysetting")
    @RequestMapping(value = {"listParam"})
    public String listParam(@RequestParam int id, @RequestParam String activityName, HttpServletRequest request, Model model){
        model.addAttribute("list", activityParameterService.findActivityParameterByActivityId(id));
        model.addAttribute("activityId", id);
        model.addAttribute("activityName", activityName);
        return "modules/system/activitysetting/param/paramList";
    }

    /**
     * 跳转添加参数页面
     */
    @RequiresUser
    @RequestMapping( value = "addParamForm")
    public String addParamForm(@ModelAttribute(value = "parameter")ActivityParameter parameter, @RequestParam int activityId, Model model) {
        parameter.setActivityId(activityId);
        return "modules/system/activitysetting/param/addParamForm";
    }

    /**
     * 添加活动配置
     */
    @RequiresPermissions(value = "activitysetting_add")
    @RequestMapping( value = "addParam")
    public String addParam(@ModelAttribute(value = "parameter") ActivityParameter parameter, RedirectAttributes redirectAttributes, Model model) {
        if(!beanValidator(model, parameter)) {
            return addParamForm(parameter, parameter.getActivityId(), model);
        }
        if(activityParameterService.addActivityParameter(parameter)) {
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common","common.save.success")));
            redirectAttributes.addFlashAttribute("parameter", new ActivityParameter(parameter.getActivityId()));
        }
        else {
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common","common.save.failed")));
            redirectAttributes.addFlashAttribute("parameter", parameter);
        }

        redirectAttributes.addAttribute("activityId", parameter.getActivityId());
        return "redirect:" + Global.getAdminPath() + "/system/activitysetting/addParamForm";
    }

    /**
     * 跳转修改参数页面
     */
    @RequiresUser
    @RequestMapping(value = "updateParamForm")
    public String updateParamForm(@ModelAttribute(value = "parameter") ActivityParameter parameter, Model model) {
        // 查找activityParameter
        parameter = activityParameterService.findActivityParameterByParamId(parameter.getId());
        model.addAttribute("parameter", parameter);
        return "modules/system/activitysetting/param/updateParamForm";
    }

    /**
     * 修改参数
     */
    @RequiresPermissions(value = "activitysetting_update")
    @RequestMapping( value = "updateParam")
    public String updateParam(ActivityParameter parameter, RedirectAttributes redirectAttributes, Model model){
        if(!beanValidator(model, parameter)){
            return updateParamForm(parameter, model);
        }
        if(activityParameterService.updateActivityParameter(parameter)) {
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common","common.save.success")));
        }
        else {
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common","common.save.failed")));
        }
        redirectAttributes.addFlashAttribute("parameter", parameter);
        return "redirect:" + Global.getAdminPath() + "/system/activitysetting/updateParamForm";
    }

    /**
     * 删除参数
     */
    @RequiresPermissions(value = "activitysetting_delete")
    @RequestMapping(value = "deleteParam")
    public String deleteParam(int id, String activityName, RedirectAttributes redirectAttributes, Model model) {
        int activityId = activityParameterService.findActivityParameterByParamId(id).getActivityId();
        if(activityParameterService.deleteActivityParameter(id)){
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common", "common.delete.success")));
        }
        else {
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common", "common.delete.failed")));
        }
        return "redirect:" + Global.getAdminPath() + "/system/activitysetting/listParam?id=" + activityId + "&activityName=" + activityName;
    }

    //////////////集合参数值///////////////
    /**
     * 显示集合参数值
     */
    @RequiresUser
    @RequestMapping(value = "listParamValue")
    public String listParamValue(HttpServletRequest request, Model model){
        int paramId = Integer.valueOf(request.getParameter("paramId"));
        ActivityParameter parameter = activityParameterService.findActivityParameterByParamId(paramId);
        parameter.setValues(parameterListService.findActivityParameterValues(paramId));
        model.addAttribute("parameter", parameter);
        return "modules/system/activitysetting/param/paramValueList";
    }

    @RequiresPermissions(value = "activitysetting_update")
    @RequestMapping(value = "saveParamValue")
    public String saveParamValue(@ModelAttribute ActivityParameter parameter, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {
        if(parameterListService.saveActivityParameterValues(parameter))
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString("common", "common.save.success")));
        else
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString("common", "common.save.failed")));
        return "redirect:" + Global.getAdminPath() + "/system/activitysetting/listParamValue?paramId=" + parameter.getId();
    }
}
