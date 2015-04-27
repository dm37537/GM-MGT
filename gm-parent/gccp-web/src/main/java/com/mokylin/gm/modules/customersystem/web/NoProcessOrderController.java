package com.mokylin.gm.modules.customersystem.web;


import com.mokylin.gm.modules.customersystem.entity.OrderStatus;
import com.mokylin.gm.modules.customersystem.entity.QueryCondition;
import com.mokylin.gm.modules.customersystem.entity.WorkOrder;
import com.mokylin.gm.modules.customersystem.service.OrderService;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 不处理工单相关Controller
 * Created by Administrator on 2014/7/17.
 */
@Controller
@RequiresUser
@RequestMapping(value = "/customersystem/noprocessorder")
public class NoProcessOrderController extends BaseController{

    @Resource
    private OrderService orderService;

    @RequiresPermissions(value = "noprocessorder")
    @RequestMapping(value = {"list",""})
    public String list(@ModelAttribute(value = "queryCondition")QueryCondition queryCondition, Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("page", orderService.findOrderByStatus(new Page<WorkOrder>(request, response), queryCondition, OrderStatus.NoProcess));
        return "modules/customersystem/noprocessorder/noprocessList";
    }
}
