package com.mokylin.gm.modules.customersystem.web;

import com.mokylin.gm.modules.customersystem.entity.OrderStatus;
import com.mokylin.gm.modules.customersystem.entity.WorkOrder;
import com.mokylin.gm.modules.customersystem.service.OrderService;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 工单统计相关 Controller
 * Created by Administrator on 2014/7/17.
 */
@Controller
@RequiresUser
@RequestMapping(value = "/customersystem/ordercount")
public class CountOrderController extends BaseController{

    @Resource
    private OrderService orderService;

    @RequiresPermissions(value = "ordercount")
    @RequestMapping(value = {"list",""})
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "modules/customersystem/ordercount/countList";
    }

}
