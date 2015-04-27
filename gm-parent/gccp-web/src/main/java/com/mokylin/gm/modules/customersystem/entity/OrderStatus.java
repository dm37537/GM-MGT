package com.mokylin.gm.modules.customersystem.entity;

/**
 * 工单状态枚举
 * Created by Administrator on 2014/7/17.
 */
public enum OrderStatus {

    UnKnow((byte)-1),  //未知
    Pending((byte)0),  //待处理
    Reply((byte)1),    //已回复
    Forward((byte)2),  //已转发
    Processed((byte)3),//已处理
    Closed((byte)4),   //已关闭
    NoProcess((byte)5);//不处理

    private byte status;

    private OrderStatus(byte status) {
        this.status = status;

    }

    public static OrderStatus valueOf(short status) {
        for (OrderStatus item : values()) {
            if(item.status == status)
                return item;
        }
        return UnKnow;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
