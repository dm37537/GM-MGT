package com.mokylin.gm.modules.gameswitch.web;

import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2014/7/10.
 */
@Controller
@RequiresUser
@RequestMapping(value = "/gameswitch/switchmanage")
public class SwitchManageController extends BaseController {

    @RequiresUser
    @RequestMapping(value = {"index", ""})
    public String index(Model model){
        return "modules/gameswitch/switchmanage/index";
    }
}
