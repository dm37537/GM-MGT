package com.mokylin.gm.modules.customersystem.entity;

import java.util.Date;

public class WorkOrder {
    // 工单编号
    private long id;

    //工单标题
    private String title;

    // 工单类型
    private byte type;

    // 工单描述
    private String descri;

    // 创建时间
    private Date create_time;

    // 工单紧急程度
    private String level;

    // 工单状态
    private OrderStatus state;

    // 附件地址
    private String attachment_url;

    // 游戏id
    private long super_id;

    // 代理商Id
    private long agent_id;

    // 游戏区id
    private long game_id;

    // 角色名字
    private String roleName;

    // 角色昵称
    private String roleNickName;

    // 角色ID
    private long roleId;

    // 角色类型,普通玩家、vip
    private byte roleType;

    // 满意度
    private int satisfaction;

    // 备注
    private String remark;

    //处理人
    private String acceptUser;

    // 处理时间
    private Date acceptTime;

    // 最后处理人
    private String final_actor_user;

    // 最后处理时间
    private Date final_actor_time;

    // 问题类型
    private byte questionType;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getDescri() {
        return descri;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setState(OrderStatus state) {
        this.state = state;
    }

    public OrderStatus getState() {
        return state;
    }

    public void setAttachment_url(String attachment_url) {
        this.attachment_url = attachment_url;
    }

    public String getAttachment_url() {
        return attachment_url;
    }

    public void setSuper_id(long super_id) {
        this.super_id = super_id;
    }

    public long getSuper_id() {
        return super_id;
    }

    public void setAgent_id(long agent_id) {
        this.agent_id = agent_id;
    }

    public long getAgent_id() {
        return agent_id;
    }

    public void setGame_id(long game_id) {
        this.game_id = game_id;
    }

    public long getGame_id() {
        return game_id;
    }

    public String getRoleNickName() {
        return roleNickName;
    }

    public void setRoleNickName(String roleNickName) {
        this.roleNickName = roleNickName;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public byte getRoleType() {
        return roleType;
    }

    public void setRoleType(byte roleType) {
        this.roleType = roleType;
    }

    public void setRoleName(String role_name) {
        this.roleName = role_name;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public String getAcceptUser() {
        return acceptUser;
    }

    public void setAcceptUser(String acceptUser) {
        this.acceptUser = acceptUser;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public void setFinal_actor_user(String final_actor_user) {
        this.final_actor_user = final_actor_user;
    }

    public String getFinal_actor_user() {
        return final_actor_user;
    }

    public void setFinal_actor_time(Date final_actor_time) {
        this.final_actor_time = final_actor_time;
    }

    public Date getFinal_actor_time() {
        return final_actor_time;
    }

    public byte getQuestionType() {
        return questionType;
    }

    public void setQuestionType(byte questionType) {
        this.questionType = questionType;
    }
}

