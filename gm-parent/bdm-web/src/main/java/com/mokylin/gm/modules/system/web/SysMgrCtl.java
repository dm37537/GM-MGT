package com.mokylin.gm.modules.system.web;

import com.mokylin.gm.config.Global;
import com.mokylin.gm.entity.MessageType;
import com.mokylin.gm.entity.ResultMsg;
import com.mokylin.gm.modules.system.entity.Sys;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.service.SysMgrService;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.ResourceManager;
import com.mokylin.gm.utils.StringUtils;
import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Controller
@RequestMapping("sysMgr")

public class SysMgrCtl extends BaseController {
    @Resource
    SysMgrService sysMgrService;


    @RequestMapping(value = {"list",""})
    public String list(Sys sys,HttpServletRequest request,HttpServletResponse response,Model model){
        Page<Sys> page = sysMgrService.pageList(new Page<Sys>(request, response), sys);
        model.addAttribute("page", page);
        return "modules/system/sysMgr/sysList";
    }
    /**
     * message状态:1-成功，0失败
     */
    @RequiresUser
    @RequestMapping(value = "edit")
    @RequiresPermissions("common.menu.sysMgr.oper")
    public String edit(Sys sys,HttpServletRequest request,HttpServletResponse response,Model model) {
        model.addAttribute("sys",sysMgrService.findSysById(sys.getId()));
        return "modules/system/sysMgr/sysEdit";
    }
    /**
     * message状态:1-成功，0失败
     */
    @RequiresUser
    @RequestMapping(value = "add")
    @RequiresPermissions("common.menu.sysMgr.oper")
    public String add(Sys sys,HttpServletRequest request,HttpServletResponse response,Model model) {

        return "modules/system/sysMgr/sysAdd";
    }
    @RequiresUser
    @RequestMapping(value = "update")
    @RequiresPermissions("common.menu.sysMgr.oper")
    public String update(Sys sys,HttpServletRequest request,HttpServletResponse response,Model model) {
        ResultMsg msg;
        if(sysMgrService.updateSysById(sys)) {
            msg = new ResultMsg(1, MessageType.SUCCESS, "保存系统信息成功");
        }else{
            msg = new ResultMsg(0, MessageType.ERROR, "保存系统信息失败");
            model.addAttribute("message", msg);
            model.addAttribute("sys",sys);
        }

        return "redirect:" + Global.getAdminPath() + "/sysMgr/list";
    }
    @RequiresUser
    @RequestMapping(value = "save")
    @RequiresPermissions("common.menu.sysMgr.oper")
    public String save(Sys sys,HttpServletRequest request,HttpServletResponse response,Model model) {
        ResultMsg msg;
        if(sysMgrService.save(sys)) {
            msg = new ResultMsg(1, MessageType.SUCCESS, "保存系统信息成功");
        }else{
            msg = new ResultMsg(0, MessageType.ERROR, "保存系统信息失败");
            model.addAttribute("message", msg);
            model.addAttribute("sys",sys);
        }
        return "redirect:" + Global.getAdminPath() + "/sysMgr/list";
    }
    @RequiresUser
    @RequestMapping(value = "delete")
    @RequiresPermissions("common.menu.sysMgr.oper")
    public String delete(Sys sys,HttpServletRequest request,HttpServletResponse response,Model model) {
        ResultMsg msg;
        if(sysMgrService.deleteSysById(sys.getId())) {
            msg = new ResultMsg(1, MessageType.SUCCESS, "删除系统成功");
        }else{
            msg = new ResultMsg(0, MessageType.ERROR, "删除系统失败");
        }
        model.addAttribute("message", msg);
        model.addAttribute("sys",sys);
        return "redirect:" + Global.getAdminPath() + "/sysMgr/list";
    }

}
