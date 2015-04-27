package com.mokylin.gm.modules.customersystem.service;

import com.mokylin.gm.modules.customersystem.dao.OrderDao;
import com.mokylin.gm.modules.customersystem.entity.OrderStatus;
import com.mokylin.gm.modules.customersystem.entity.QueryCondition;
import com.mokylin.gm.modules.customersystem.entity.WorkOrder;
import com.mokylin.gm.service.base.BaseGmService;
import com.mokylin.gm.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 工单 Service
 * Created by Administrator on 2014/7/17.
 */
@Service
public class OrderService extends BaseGmService{

    @Resource
    private OrderDao orderDao;

    public Page<WorkOrder> findOrderByStatus(Page<WorkOrder> page, QueryCondition queryCondition, OrderStatus pending) {
        return orderDao.findOrderByStatus(page, queryCondition, pending);
    }
}
