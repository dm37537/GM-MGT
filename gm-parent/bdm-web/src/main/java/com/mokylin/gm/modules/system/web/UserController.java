package com.mokylin.gm.modules.system.web;

import com.mokylin.gm.entity.MessageType;
import com.mokylin.gm.entity.ResultMsg;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.service.UserService;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.utils.ResourceManager;
import com.mokylin.gm.utils.StringUtils;
import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户Controller
 * Created by Administrator on 2014/6/18.
 */
@Controller
@RequestMapping(value = "/system/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * Home page
     */
    @RequiresUser
    @RequestMapping(value = "home")
    public String home(User user, Model model, HttpServletRequest request, HttpServletResponse response) {
        return "modules/system/home";
    }

    /**
     * message状态:1-成功，0失败
     */
    @RequiresUser
    @RequestMapping(value = "info")
    public String info(User user, Model model, HttpServletRequest request, HttpServletResponse response) {
        User currentUser = UserUtils.getUser();
        //用户修改个人信息
        if (StringUtils.isNotBlank(user.getName())) {
            currentUser = UserUtils.getUser(true);
            currentUser.setEmail(user.getEmail());
            currentUser.setMobile(user.getMobile());
            ResultMsg msg;
            if(userService.updateUser(currentUser)) {
                msg = new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString(request, "system","system.save.userinfo.success"));
            }else {
                msg = new ResultMsg(0, MessageType.SUCCESS, ResourceManager.getString(request, "system","system.save.userinfo.failed"));
            }
            model.addAttribute("message", msg);
        }
        model.addAttribute("user", currentUser);
        return "modules/system/userInfo";
    }

    /**
     *
     */
    @RequiresUser
    @RequestMapping(value = "modifyPwd")
    public String modifyPwd(String oldPassword, String newPassword, Model model, HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtils.getUser();
        ResultMsg msg;
        if(StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
            if(UserService.validatePassword(oldPassword, user.getPassword())) {
                if(userService.updatePasswordById(user.getId(), user.getUserName(), newPassword)) {
                    msg = new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString(request, "system","system.modify.password.success"));
                }else{
                    msg = new ResultMsg(0, MessageType.ERROR, ResourceManager.getString(request, "system","system.save.userinfo.failed"));
                }
            }
            else {
                msg = new ResultMsg(0, MessageType.ERROR, ResourceManager.getString(request, "system","system.modify.password.tip1"));
            }
            model.addAttribute("message", msg);
        }
        model.addAttribute("user", user);
        return "modules/system/userModifyPwd";
    }
}
