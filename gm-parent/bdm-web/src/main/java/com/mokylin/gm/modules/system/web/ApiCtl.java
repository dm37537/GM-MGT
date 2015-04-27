package com.mokylin.gm.modules.system.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mokylin.gm.modules.system.entity.*;
import com.mokylin.gm.modules.system.model.ApiResultModel;
import com.mokylin.gm.modules.system.model.Pms_config;
import com.mokylin.gm.modules.system.service.*;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.StringUtils;
import com.mokylin.gm.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Controller
@RequestMapping("/api")
public class ApiCtl extends BaseController {
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
    @Resource
    UserService userService;
    @Resource
    GameMgrService gameMgrService;
    @Resource
    GameParamMgrService gameParamMgrService;
    @RequestMapping("/user/verify")
    @ResponseBody
    public String userVerify(HttpServletRequest request){
        String result="false";
        String userName=request.getParameter("user_name");
        String passWord=request.getParameter("pass_word");
        String sysId=request.getParameter("sys_id");
        //如果验证失败 返回false 如果成功则返回用户的所有权限项（用户权限和所有角色权限的合集）
        User user=userService.getUserByUserName(userName);
        if(user!=null&&user.getPassword().equals(passWord)){
            //验证成功
            //获取所有的权限项
            Map<String,Pms_config> configMap= Maps.newHashMap();
            Map<Integer,Pms_item> pmsItemMap = pmsItemMgrService.mapPmsItem(Integer.parseInt(sysId));

            //混合用户所有的角色权限
            List<Role> roles=roleConfigService.findAllRole(user.getId(),Integer.parseInt(sysId));
            for (Role role:roles) {
                List<Role_config> configs=roleConfigService.findConfigByRoleId(role.getId());
                for (Role_config config:configs) {
                    Pms_item item=pmsItemMap.get(config.getConfig_item_id());
                    if(config.getType()==1&&item!=null){//开关类型
                        Pms_config pms_config=configMap.get(item.getPms_item_name());
                        if(pms_config==null){
                            pms_config=new Pms_config(item.getPms_item_name(),config.getConfig_item_value(),1);
                            configMap.put(item.getPms_item_name(),pms_config);
                        }
                    }else if(config.getType()==2&&item!=null) {//范围类型 需要混合值
                        Pms_config pms_config=configMap.get(item.getPms_item_name());
                        if(pms_config==null){
                            pms_config=new Pms_config(item.getPms_item_name(),config.getConfig_item_value(),2);
                            configMap.put(item.getPms_item_name(),pms_config);
                        }else{
                            pms_config.setValue(combineString(pms_config.getValue(),config.getConfig_item_value()));
                            configMap.put(item.getPms_item_name(),pms_config);
                        }
                    }
                }
            }
            //混合用户的权限
            List<User_config> userConfigs=userMgrService.findConfigByUserId(user.getId());
            for(User_config config:userConfigs){
                Pms_item item=pmsItemMap.get(config.getConfig_item_id());
                if(config.getType()==1&&item!=null){//开关类型
                    Pms_config pms_config=configMap.get(item.getPms_item_name());
                    if(pms_config==null){
                        pms_config=new Pms_config(item.getPms_item_name(),config.getConfig_item_value(),1);
                        configMap.put(item.getPms_item_name(),pms_config);
                    }
                }else if(config.getType()==2&&item!=null) {//范围类型 需要混合值
                    Pms_config pms_config=configMap.get(item.getPms_item_name());
                    if(pms_config==null){
                        pms_config=new Pms_config(item.getPms_item_name(),config.getConfig_item_value(),2);
                        configMap.put(item.getPms_item_name(),pms_config);
                    }else{
                        pms_config.setValue(combineString(pms_config.getValue(),config.getConfig_item_value()));
                        configMap.put(item.getPms_item_name(),pms_config);
                    }
                }
            }
            user.setPermissions(configMap);
//            List<Pms_config> configList=Lists.newArrayList();
//            for (Pms_config config : configMap.values()) {
//                configList.add(config);
//            }
            result= JSON.toJSONString(user);
        }
        return result;
    }
    @RequestMapping("/game/down")
    @ResponseBody
    public String gameDown(HttpServletRequest request){
        List<Game> sortedList=Lists.newArrayList();
        String result="";
        String super_id=request.getParameter("super_id");
        if(super_id!=null&&StringUtils.isNumeric(super_id)){
            List<Game> games=gameMgrService.getAllGameTreeDatas(Long.parseLong(super_id));
            Game.sortList(sortedList,games,0);
            List<Game_param> params=gameParamMgrService.findAllParam(Long.parseLong(super_id));
            Game.params2Game(sortedList,params);
        }else{
            List<Game> games=gameMgrService.getAllGameTreeDatas();
            Game.sortList(sortedList,games,0);
            List<Game_param> params=gameParamMgrService.findAllParam();
            Game.params2Game(sortedList,params);
        }
        return JSON.toJSONString(sortedList);
    }
    @RequestMapping("/user/pms")
    @ResponseBody
    public String userPms(HttpServletRequest request){
        String result="";
        String userName=request.getParameter("user_name");
        String sysId=request.getParameter("sys_id");
        User user=userService.getUserByUserName(userName);
        if(user!=null&&StringUtils.isNumeric(sysId)){
            //验证成功
            //获取所有的权限项
            Map<String,Pms_config> configMap= Maps.newHashMap();
            Map<Integer,Pms_item> pmsItemMap = pmsItemMgrService.mapPmsItem(Integer.parseInt(sysId));
            //混合用户所有的角色权限
            List<Role> roles=roleConfigService.findAllRole(user.getId(),Integer.parseInt(sysId));
            for (Role role:roles) {
                List<Role_config> configs=roleConfigService.findConfigByRoleId(role.getId());
                for (Role_config config:configs) {
                    Pms_item item=pmsItemMap.get(config.getConfig_item_id());
                    if(config.getType()==1&&item!=null){//开关类型
                        Pms_config pms_config=configMap.get(item.getPms_item_name());
                        if(pms_config==null){
                            pms_config=new Pms_config(item.getPms_item_name(),config.getConfig_item_value(),1);
                            configMap.put(item.getPms_item_name(),pms_config);
                        }
                    }else if(config.getType()==2&&item!=null) {//范围类型 需要混合值
                        Pms_config pms_config=configMap.get(item.getPms_item_name());
                        if(pms_config==null){
                            pms_config=new Pms_config(item.getPms_item_name(),config.getConfig_item_value(),2);
                            configMap.put(item.getPms_item_name(),pms_config);
                        }else{
                            pms_config.setValue(combineString(pms_config.getValue(),config.getConfig_item_value()));
                            configMap.put(item.getPms_item_name(),pms_config);
                        }
                    }
                }
            }
            //混合用户的权限
            List<User_config> userConfigs=userMgrService.findConfigByUserId(user.getId());
            for(User_config config:userConfigs){
                Pms_item item=pmsItemMap.get(config.getConfig_item_id());
                if(config.getType()==1&&item!=null){//开关类型
                    Pms_config pms_config=configMap.get(item.getPms_item_name());
                    if(pms_config==null){
                        pms_config=new Pms_config(item.getPms_item_name(),config.getConfig_item_value(),1);
                        configMap.put(item.getPms_item_name(),pms_config);
                    }
                }else if(config.getType()==2&&item!=null) {//范围类型 需要混合值
                    Pms_config pms_config=configMap.get(item.getPms_item_name());
                    if(pms_config==null){
                        pms_config=new Pms_config(item.getPms_item_name(),config.getConfig_item_value(),2);
                        configMap.put(item.getPms_item_name(),pms_config);
                    }else{
                        pms_config.setValue(combineString(pms_config.getValue(),config.getConfig_item_value()));
                        configMap.put(item.getPms_item_name(),pms_config);
                    }
                }
            }
            List<Pms_config> configList=Lists.newArrayList();
            for (Pms_config config : configMap.values()) {
                configList.add(config);
            }
            result= JSON.toJSONString(configList);
        }
        return  result;
    }
    @RequestMapping("/game/down/update")
    @ResponseBody
    public String gameDownUpdate(HttpServletRequest request){
        List<Game> sortedList=Lists.newArrayList();
        String result="";
        String super_id=request.getParameter("super_id");
        String date=request.getParameter("date");
        if(super_id!=null&&StringUtils.isNumeric(super_id)){
            List<Game> games=gameMgrService.getAllGameTreeDatas(Long.parseLong(super_id),date);
            List<Game_param> params=gameParamMgrService.findAllParam(Long.parseLong(super_id));
            Game.params2Game(games,params);
            return JSON.toJSONString(games);
        }else{
            List<Game> games=gameMgrService.getAllGameTreeDatas(date);
            List<Game_param> params=gameParamMgrService.findAllParam();
            Game.params2Game(games,params);
            return JSON.toJSONString(games);
        }

    }
    @RequestMapping("/user/reset/password")
    @ResponseBody
    public String gameResetPassword(HttpServletRequest request){
        String result="false";
        String userName=request.getParameter("user_name");
        String old_password=request.getParameter("old_password");
        String new_password=request.getParameter("new_password");
        //如果验证失败 返回false 如果成功则返回用户的所有权限项（用户权限和所有角色权限的合集）
        User user=userService.getUserByUserName(userName);
        if(user!=null&&user.getPassword().equals(old_password)){
           result=userService.updatePasswordByIdEncoded(user.getId(),userName,new_password)+"";
        }
        return result;
    }
    @RequestMapping("/user/add")
    @ResponseBody
    public String gameAdd(HttpServletRequest request){
        String result="false";
        String userJson=request.getParameter("user");
        User user= (User) JSON.parse(userJson);
        if(userService.getUserByUserName(user.getUserName())!=null){
            result=userMgrService.save(user)+"";
        }
        return result;
    }
    @RequestMapping("/user/set/role")
    @ResponseBody
    public String gameSetRole(HttpServletRequest request){
        String result="false";
        String user_name=request.getParameter("user_name");
        String role_id=request.getParameter("role_id");
        String sys_id=request.getParameter("sys_id");
        if(StringUtils.isNumeric(role_id)&&StringUtils.isNumeric(sys_id)){
            Role role=roleMgrService.findById(Integer.parseInt(role_id));
            if(role!=null&&sys_id.equals(""+role.getSys_id())){
                User user=userService.getUserByUserName(user_name);
                if(user!=null){
                    List<User> users=Lists.newArrayList();
                    users.add(user);
                    result=roleMgrService.addUsers(users,Integer.parseInt(role_id))+"";
                }
            }
        }

        return result;
    }
    @RequestMapping("/user/forbid")
    @ResponseBody
    public String gameDelete(HttpServletRequest request){
        String result="false";
        String user_name=request.getParameter("user_name");
        User user=userService.getUserByUserName(user_name);
        if(user!=null){
            result= userMgrService.updateStatus(user.getId(),0)+"";
        }
        return result;
    }
    private String combineString(String s1,String s2){
        Set<String> set= Sets.newHashSet();
        String result="";
        list2set(set,Arrays.asList(s1.split(",")));
        list2set(set,Arrays.asList(s2.split(",")));
        for (String str : set) {
            result+=str+",";
        }
        return result;
    }
    private void list2set( Set set,List list){
        for (int i = 0; i < list.size(); i++) {
            set.add(list.get(i));
        }
    }
}
