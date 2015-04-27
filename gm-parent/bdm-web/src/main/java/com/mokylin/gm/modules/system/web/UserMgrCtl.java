package com.mokylin.gm.modules.system.web;

import com.google.common.collect.Lists;
import com.mokylin.gm.config.Global;
import com.mokylin.gm.entity.MessageType;
import com.mokylin.gm.entity.ResultMsg;
import com.mokylin.gm.modules.system.entity.*;
import com.mokylin.gm.modules.system.service.*;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.StringUtils;
import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2014/6/27.
 */
@Controller
@RequestMapping("/userMgr")
public class UserMgrCtl extends BaseController {
    @Resource
    UserMgrService userMgrService;
    @Resource
    RoleMgrService roleMgrService;
    @Resource
    UserService userService;
    @Resource
    SysMgrService sysMgrService;
    @Resource
    PmsItemMgrService pmsItemMgrService;
    @RequestMapping(value = {"list",""})
    @RequiresPermissions("common.menu.userMgr.oper")
    public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<User> page = userMgrService.pageList(new Page<User>(request, response), user);
        model.addAttribute("page", page);
        return "modules/system/userMgr/userList";
    }
    @RequestMapping("add")
    @RequiresPermissions("common.menu.userMgr.oper")
    public String add(User user,Model model){
        model.addAttribute("user", new User());
        return "modules/system/userMgr/userAdd";
    }
    @RequestMapping("edit")
    @RequiresPermissions("common.menu.userMgr.oper")
    public String edit( HttpServletRequest request,Model model){
        String userId = request.getParameter("userId");
        if(StringUtils.isNumeric(userId)) {
            User user=userService.getUserById(Long.parseLong(userId));
            model.addAttribute("user",user);
        }
        return "modules/system/userMgr/userEdit";
    }
    @RequestMapping("save")
    @RequiresPermissions("common.menu.userMgr.oper")
         public String save(User user,Model model){
        boolean save = userMgrService.save(user);
        return "redirect:" + Global.getAdminPath() + "/userMgr/list";
    }
    @RequestMapping("update")
    @RequiresPermissions("common.menu.userMgr.oper")
    public String update(User user,Model model){
        boolean save = userMgrService.update(user);
        return "redirect:" + Global.getAdminPath() + "/userMgr/list";
    }
    @RequestMapping("findUserByRoleId")
    @RequiresPermissions("common.menu.userMgr.oper")
    public String findUserByRoleId(User user,HttpServletRequest request, HttpServletResponse response,Model model){
        String roleId = request.getParameter("roleId");
        if(StringUtils.isNumeric(roleId)) {
            Page<User> page = userMgrService.findUserByRoleId(new Page<User>(request, response), user,Integer.parseInt(roleId));
            model.addAttribute("page",page);
            model.addAttribute("role",roleMgrService.findById(Integer.parseInt(roleId)));
        }
        return "modules/system/userMgr/findUserByRoleId";
    }

    @RequestMapping("updateStatus")
    @RequiresPermissions("common.menu.userMgr.oper")
    public String updateStatus(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        String userId=request.getParameter("userId");
        String status=request.getParameter("status");
//        ResultMsg msg;
        if(StringUtils.isNumeric(userId)&&StringUtils.isNumeric(status)&&userMgrService.updateStatus(Integer.parseInt(userId),Integer.parseInt(status))){
//            msg=new ResultMsg(1, MessageType.SUCCESS, "更新["+userService.getUserById4All(Long.parseLong(userId)).getName()+"]状态成功");
        }else{
//            msg = new ResultMsg(0, MessageType.ERROR, "更新["+userService.getUserById4All(Long.parseLong(userId)).getName()+"]状态失败");
        }
//        model.addAttribute("message",msg);
        return "redirect:" + Global.getAdminPath() + "/userMgr/list";
    }
    @RequestMapping("resetPsw")
    @RequiresPermissions("common.menu.userMgr.oper")
    public String resetPsw(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        String userId=request.getParameter("userId");
        ResultMsg msg;
        if(StringUtils.isNumeric(userId)&&userMgrService.resetPsw(Integer.parseInt(userId))){
//            msg=new ResultMsg(1, MessageType.SUCCESS, "重置["+userService.getUserById4All(Long.parseLong(userId)).getName()+"]密码成功");
        }else{
//            msg = new ResultMsg(0, MessageType.ERROR, "重置["+userService.getUserById4All(Long.parseLong(userId)).getName()+"]密码失败");
        }
//        model.addAttribute("message",msg);
        return "redirect:" + Global.getAdminPath() + "/userMgr/list";
    }
    @RequestMapping("assignUserPms")
    @RequiresPermissions("common.menu.userMgr.oper")
    public String assignUserPms(HttpServletRequest request, HttpServletResponse response,Model model){
        //查询所有系统
        String userId=request.getParameter("userId");
        List<Sys> sysList=sysMgrService.listAll();
        model.addAttribute("sysList",sysList);
        model.addAttribute("user",userService.getUserById4All(Long.parseLong(userId)));
        return "modules/system/userMgr/assignUserPms";
    }
    @RequestMapping("assignPmsItem4User")
    @RequiresPermissions("common.menu.userMgr.oper")
    public String assignPmsItem4Role(HttpServletRequest request, HttpServletResponse response, Model model) {
        //找到所有的权限项目
        String userId = request.getParameter("userId");
        String sysId = request.getParameter("sysId");
        List<Pms_item> pmsItems = pmsItemMgrService.listUseAbleBySysIdState(0, Integer.parseInt(sysId));
        List<Pms_item> sortedList= Lists.newArrayList();
        Pms_item.sortList(sortedList,pmsItems,0);
        //根据角色id 获取已经选择的权限集合
        List<User_config> configs=userMgrService.findConfigByUserId(Integer.parseInt(userId));;
        for (int i = 0; i < sortedList.size(); i++) {
            Pms_item item=sortedList.get(i);
            if(item.getPms_item_parent_id()==0){
                continue;
            }
            for (int j = 0; j < configs.size(); j++) {
                User_config config=configs.get(j);
                if(item.getId()==config.getConfig_item_id()){
                    item.setChecked(true);
                    item.setConfigValue(config.getConfig_item_value());
                    break;
                }
            }
        }
        model.addAttribute("sortedList", sortedList);
        model.addAttribute("user", userService.getUserById4All(Long.parseLong(userId)));
        model.addAttribute("sys",sysMgrService.findSysById(Integer.parseInt(sysId)));
        return "modules/system/userMgr/assignPmsItem4User";
    }
}
