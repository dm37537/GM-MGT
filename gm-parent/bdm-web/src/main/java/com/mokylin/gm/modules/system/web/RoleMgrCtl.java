package com.mokylin.gm.modules.system.web;

import com.google.common.collect.Lists;
import com.mokylin.gm.modules.system.entity.Pms_item;
import com.mokylin.gm.modules.system.entity.Role;
import com.mokylin.gm.modules.system.entity.Role_config;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.service.*;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.StringUtils;
import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Controller
@RequestMapping("/roleMgr")
public class RoleMgrCtl extends BaseController {
    @Resource
    RoleMgrService roleMgrService;
    @Resource
    SysMgrService sysMgrService;
    @Resource
    UserMgrService userMgrService;
    @Resource
    PmsItemMgrService pmsItemMgrService;
    @Resource
    PmsConfigService roleConfigService;
    @RequestMapping(value = {"list",""})
    @RequiresPermissions("common.menu.sysMgr.role.oper")
    public String list(Role role, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Role> page = roleMgrService.pageList(new Page<Role>(request, response), role);
        model.addAttribute("page", page);
        return "modules/system/roleMgr/roleList";
    }
    @RequestMapping("listBySysId")
    @RequiresPermissions("common.menu.sysMgr.role.oper")
    public String listBySysId(HttpServletRequest request, HttpServletResponse response, Model model) {
        String sysId=request.getParameter("sysId");
        if(StringUtils.isNumeric(sysId)){
            Role role=new Role();
            role.setSys_id(Integer.parseInt(sysId));
            List<Role> list = roleMgrService.listBySysId(Integer.parseInt(sysId));
            model.addAttribute("list", list);
            model.addAttribute("sys", sysMgrService.findSysById(role.getSys_id()));
        }

        return "modules/system/roleMgr/roleListBySysId";
    }
    @RequestMapping("addUsers")
    @ResponseBody
    @RequiresPermissions("common.menu.sysMgr.role.oper")
    public boolean addUsers(HttpServletRequest request, HttpServletResponse response,Model model){
        String addUserNames = request.getParameter("addUserNames");//用户名 用,逗号隔开
        String roleId = request.getParameter("roleId");
        if(StringUtils.isNumeric(roleId)) {
            //通过名字找到所有的user集合
            List<User> users=userMgrService.findUsersByUserNames(addUserNames);
            return roleMgrService.addUsers(users,Integer.parseInt(roleId));
        }else {
            return false;
        }
    }
    @RequestMapping("delUser")
    @ResponseBody
    @RequiresPermissions("common.menu.sysMgr.role.oper")
    public boolean delUser(HttpServletRequest request, HttpServletResponse response,Model model){
        String userId = request.getParameter("userId");
        String roleId = request.getParameter("roleId");
        if(StringUtils.isNumeric(roleId)&&StringUtils.isNumeric(userId)) {
            return roleMgrService.delUser(Integer.parseInt(userId),Integer.parseInt(roleId));
        }else {
            return false;
        }
    }
    @RequestMapping("addRole")
    @ResponseBody
    @RequiresPermissions("common.menu.sysMgr.role.oper")
    public boolean addRole(HttpServletRequest request, HttpServletResponse response,Model model){
        String roleName = request.getParameter("roleName");
        roleName=StringUtils.unescape(roleName);
        String sysId = request.getParameter("sysId");
        if(StringUtils.isNumeric(sysId)) {
            return roleMgrService.addRole(roleName, Integer.parseInt(sysId));
        }else {
            return false;
        }
    }
    @RequestMapping("delRole")
    @ResponseBody
    @RequiresPermissions("common.menu.sysMgr.role.oper")
    public boolean delRole(HttpServletRequest request, HttpServletResponse response,Model model){
        String roleId = request.getParameter("roleId");
        if(StringUtils.isNumeric(roleId)) {
            return roleMgrService.delRole(Integer.parseInt(roleId));
        }else {
            return false;
        }
    }
//    @RequestMapping("addConfig")
//    @ResponseBody
//    public boolean addConfig(HttpServletRequest request, HttpServletResponse response,Model model){
//        String roleId = request.getParameter("roleId");
//        String pmsItemIds = request.getParameter("pmsItemIds");
//        if(StringUtils.isNumeric(roleId)) {
//            return roleMgrService.addConfig(Integer.parseInt(roleId),pmsItemIds);
//        }else {
//            return false;
//        }
//    }
//    @RequestMapping("addConfig4Type2")
//    @ResponseBody
//    public boolean addConfig4Type2(HttpServletRequest request, HttpServletResponse response,Model model){
//        String roleId = request.getParameter("role_id");
//        String config_item_id = request.getParameter("config_item_id");
//        String config_item_value = request.getParameter("config_item_value");
//        if(StringUtils.isNumeric(roleId)&&StringUtils.isNumeric(config_item_id)) {
//            return roleConfigService.addConfig4RoleTyp2(Integer.parseInt(roleId),Integer.parseInt(config_item_id),config_item_value);
//        }else {
//            return false;
//        }
//    }
    @RequestMapping("assignPmsItem4Role")
    @RequiresPermissions("common.menu.sysMgr.role.oper")
    public String assignPmsItem4Role(HttpServletRequest request, HttpServletResponse response, Model model) {
        //找到所有的权限项目
        String roleId = request.getParameter("roleId");
        String sysId = request.getParameter("sysId");
        List<Pms_item> pmsItems = pmsItemMgrService.listUseAbleBySysIdState(0, Integer.parseInt(sysId));
        List<Pms_item> sortedList= Lists.newArrayList();
        Pms_item.sortList(sortedList,pmsItems,0);
        //根据角色id 获取已经选择的权限集合
        List<Role_config> configs=roleConfigService.findConfigByRoleId(Integer.parseInt(roleId));
        for (int i = 0; i < sortedList.size(); i++) {
            Pms_item item=sortedList.get(i);
            if(item.getPms_item_parent_id()==0){
                continue;
            }
            for (int j = 0; j < configs.size(); j++) {
                Role_config config=configs.get(j);
                if(item.getId()==config.getConfig_item_id()){
                    item.setChecked(true);
                    item.setConfigValue(config.getConfig_item_value());
                    break;
                }
            }
        }
        model.addAttribute("sortedList", sortedList);
        model.addAttribute("role", roleMgrService.findById(Integer.parseInt(roleId)));
        return "modules/system/roleMgr/assignPmsItem4Role";
    }
}
