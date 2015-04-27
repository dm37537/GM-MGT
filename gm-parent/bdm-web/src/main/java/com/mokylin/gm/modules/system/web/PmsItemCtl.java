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
import org.apache.shiro.authz.annotation.RequiresUser;
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
@RequestMapping("/pmsItemMgr")
public class PmsItemCtl extends BaseController {
    @Resource
    PmsItemMgrService pmsItemMgrService;
    @Resource
    SysMgrService sysMgrService;
    @Resource
    PmsConfigService pmsConfigService;
    @Resource
    RoleMgrService roleMgrService;
    @Resource
    UserMgrService userMgrService;
    @Resource
    GameMgrService gameMgrService;
    @Resource
    UserService userService;
    @RequestMapping("listBySysId")
    @RequiresPermissions("common.menu.sysMgr.pms.oper")
    public String listBySysId(Pms_item pmsItem, HttpServletRequest request, HttpServletResponse response, Model model) {
        String sysIdStr=request.getParameter("sysId");
        if(StringUtils.isNumeric(sysIdStr)){
            List<Pms_item> pmsItems = pmsItemMgrService.listBySysId(pmsItem, Integer.parseInt(sysIdStr));
            List<Pms_item> sortedList= Lists.newArrayList();
            Pms_item.sortList(sortedList,pmsItems,0);
            model.addAttribute("sortedList", sortedList);
            model.addAttribute("sys",sysMgrService.findSysById(Integer.parseInt(sysIdStr)));
        }
        return "modules/system/sysMgr/pmsItemTreeList";
    }
    @RequestMapping("pmsItemEdit")
    @RequiresPermissions("common.menu.sysMgr.pms.oper")
    public String pmsItemEdit(Pms_item pmsItem, HttpServletRequest request, HttpServletResponse response, Model model) {
        String pmsItemId=request.getParameter("pmsItemId");
        if(StringUtils.isNumeric(pmsItemId)){
            Pms_item item = pmsItemMgrService.findById(Integer.parseInt(pmsItemId));
            model.addAttribute("item", item);
            model.addAttribute("sys",sysMgrService.findSysById(item.getSys_id()));
            if(item.getPms_item_parent_id()!=0){
                model.addAttribute("parent_item",pmsItemMgrService.findById(item.getPms_item_parent_id()));
            }

        }
        return "modules/system/sysMgr/pmsItemEdit";
    }
    @RequestMapping("pmsItemAdd")
    @RequiresPermissions("common.menu.sysMgr.pms.oper")
    public String pmsItemAdd(Pms_item pmsItem, HttpServletRequest request, HttpServletResponse response, Model model) {
        Pms_item item=new Pms_item();
        String sysIdStr=request.getParameter("sysId");
        String pidStr=request.getParameter("pid");
        if(StringUtils.isNumeric(sysIdStr)){
            item.setSys_id(Integer.parseInt(sysIdStr));
            model.addAttribute("sys",sysMgrService.findSysById(item.getSys_id()));
        }
        if(StringUtils.isNumeric(pidStr)){
            item.setPms_item_parent_id(Integer.parseInt(pidStr));
            model.addAttribute("parent_item",pmsItemMgrService.findById(Integer.parseInt(pidStr)));
        }else{
            item.setPms_item_parent_id(0);
        }
        model.addAttribute("item", item);

        return "modules/system/sysMgr/pmsItemAdd";
    }
    @RequestMapping("updateByIdAjax")
    @ResponseBody
    public String updateByIdAjax(Pms_item pmsItem, HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("s");
        return "1";
    }
    @RequestMapping("update")
    @RequiresPermissions("common.menu.sysMgr.pms.oper")
    public String update(Pms_item pmsItem, HttpServletRequest request, HttpServletResponse response, Model model) {
        ResultMsg msg;
        if(pmsItemMgrService.update(pmsItem)) {
            msg = new ResultMsg(1, MessageType.SUCCESS, "保存信息成功");
        }else{
            msg = new ResultMsg(0, MessageType.ERROR, "保存信息失败");
        }
        model.addAttribute("message", msg);
        model.addAttribute("sys",pmsItem);
      return "redirect:" + Global.getAdminPath() + "/pmsItemMgr/listBySysId?sysId="+pmsItem.getSys_id();
    }
    @RequestMapping("save")
    @RequiresPermissions("common.menu.sysMgr.pms.oper")
    public String save(Pms_item pmsItem, HttpServletRequest request, HttpServletResponse response, Model model) {
        ResultMsg msg;
        if(pmsItemMgrService.save(pmsItem)) {
            msg = new ResultMsg(1, MessageType.SUCCESS, "保存信息成功");
        }else{
            msg = new ResultMsg(0, MessageType.ERROR, "保存信息失败");
        }
        model.addAttribute("message", msg);

        return "redirect:" + Global.getAdminPath() + "/pmsItemMgr/listBySysId?sysId="+pmsItem.getSys_id();
    }
    @RequestMapping("updateState")
    @RequiresPermissions("common.menu.sysMgr.pms.oper")
    public String updateState(HttpServletRequest request, HttpServletResponse response, Model model) {
        String sysId=request.getParameter("sysId");
        String pmsItemId=request.getParameter("id");
        String state=request.getParameter("state");
        ResultMsg msg;
        if(StringUtils.isNumeric(sysId)&&StringUtils.isNumeric(pmsItemId)&&pmsItemMgrService.updateState(Integer.parseInt(pmsItemId),Integer.parseInt(state))) {
            msg = new ResultMsg(1, MessageType.SUCCESS, "禁用权限成功");
        }else{
            msg = new ResultMsg(0, MessageType.ERROR, "禁用权限失败");
        }
        model.addAttribute("message", msg);

        return "redirect:" + Global.getAdminPath() + "/pmsItemMgr/listBySysId?sysId="+sysId;
    }
    @RequestMapping("gameTreeList4Pms")
    @RequiresUser
    public String gameTreeList4Pms(HttpServletRequest request,Model model){//游戏区 用户授权和角色授权
        //获取
        String user_id=request.getParameter("user_id");
        String config_item_id=request.getParameter("config_item_id");
        String role_id=request.getParameter("role_id");
        List<Game> games=gameMgrService.getGameTreeDatas();
        List<Game> sortedList=Lists.newArrayList();
        Game.sortList(sortedList,games,0);
        if(user_id!=null&&StringUtils.isNumeric(user_id)){
            String sys_id=request.getParameter("sys_id");
            if(StringUtils.isNumeric(user_id)&&StringUtils.isNumeric(config_item_id)&&StringUtils.isNumeric(sys_id)){
                User_config config=pmsConfigService.findUserConfig(Integer.parseInt(user_id), Integer.parseInt(config_item_id));
                if(config!=null){
                    Game.checked(sortedList,config.getConfig_item_value());
                }
                model.addAttribute("user", userService.getUserById4All(Long.parseLong(user_id)));
                model.addAttribute("sys", sysMgrService.findSysById(Integer.parseInt(sys_id)));
            }
        }else if(role_id!=null&&StringUtils.isNumeric(role_id)){

            if(StringUtils.isNumeric(role_id)&&StringUtils.isNumeric(config_item_id)){
                Role_config config=pmsConfigService.findRoleConfig(Integer.parseInt(role_id),Integer.parseInt(config_item_id));
                if(config!=null){
                    Game.checked(sortedList,config.getConfig_item_value());
                }
                model.addAttribute("role", roleMgrService.findById(Integer.parseInt(role_id)));

            }
        }

        model.addAttribute("gameTreeList",sortedList);
        model.addAttribute("pmsItem", pmsItemMgrService.findById(Integer.parseInt(config_item_id)));
        return "modules/system/gameMgr/gameTreeList4Pms";
    }
    @RequestMapping("addConfigVal")
    @ResponseBody
    @RequiresPermissions("common.menu.sysMgr.pms.oper")
    public boolean addConfigVal(HttpServletRequest request, HttpServletResponse response,Model model){//为用户或者角色的某个权限添加值
        String roleId = request.getParameter("role_id");
        String user_id = request.getParameter("user_id");
        String config_item_id = request.getParameter("config_item_id");
        String config_item_value = request.getParameter("config_item_value");
        String type=request.getParameter("type");
        String sys_id = request.getParameter("sys_id");
        if(roleId!=null&&StringUtils.isNumeric(roleId)){
            if(StringUtils.isNumeric(roleId)&&StringUtils.isNumeric(config_item_id)) {
                return pmsConfigService.addRoleConfig(Integer.parseInt(roleId),Integer.parseInt(config_item_id),config_item_value,Integer.parseInt(type));
            }else {
                return false;
            }
        }else if(user_id!=null&&StringUtils.isNumeric(user_id)){
            if(StringUtils.isNumeric(config_item_id)&&StringUtils.isNumeric(sys_id)) {
                return pmsConfigService.addUserConfig(Integer.parseInt(user_id),Integer.parseInt(config_item_id),config_item_value,Integer.parseInt(type),Integer.parseInt(sys_id));
            }else {
                return false;
            }
        }else{
            return false;
        }
    }
    @RequestMapping("addConfigs")
    @ResponseBody
    @RequiresPermissions("common.menu.sysMgr.pms.oper")
    public boolean addConfigs(HttpServletRequest request, HttpServletResponse response,Model model){//针对开关类型的批量保存
        String user_id = request.getParameter("user_id");
        String role_id = request.getParameter("role_id");
        String pmsItemIds = request.getParameter("pmsItemIds");
        String sys_id = request.getParameter("sys_id");
        if(role_id!=null&&StringUtils.isNumeric(role_id)) {
            return pmsConfigService.addRoleConfigs(Integer.parseInt(role_id),pmsItemIds);
        }else if(user_id!=null&&StringUtils.isNumeric(user_id)&&StringUtils.isNumeric(sys_id)) {
            return pmsConfigService.addUserConfigs(Integer.parseInt(user_id),pmsItemIds,Integer.parseInt(sys_id));
        }else {
            return false;
        }
    }
}
