package com.mokylin.gm.modules.activitymanage.web;

import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 活动管理Controller
 * Created by Administrator on 2014/7/1.
 */
@Controller
@RequiresUser
@RequestMapping( value = "/activitymanage")
public class ActivityManageController extends BaseController{

}
