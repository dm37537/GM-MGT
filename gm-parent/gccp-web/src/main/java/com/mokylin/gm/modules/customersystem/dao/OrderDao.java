package com.mokylin.gm.modules.customersystem.dao;

import com.mokylin.gm.modules.customersystem.entity.OrderStatus;
import com.mokylin.gm.modules.customersystem.entity.QueryCondition;
import com.mokylin.gm.modules.customersystem.entity.WorkOrder;
import com.mokylin.gm.utils.Page;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * 工单类型dao
 * Created by Administrator on 2014/7/17.
 */
@Repository
public class OrderDao extends SuperCustomerDao{

    public Page<WorkOrder> findOrderByStatus(final Page<WorkOrder> page, QueryCondition condition,final OrderStatus pending) {
        String sql = "select id, title, type, descri, create_time, `level`, state, attachment_url, super_id, agent_id, game_id, role_name, role_nick_name, role_id, role_type, " +
                "satisfaction, remark, accept_user, accept_time, final_actor_user, final_actor_time, question_type FROM t_work_order"+ getTableSuffix() +" where state=? ";
        StringBuilder builder = new StringBuilder();

        String conditionSql = " and create_time>'"+condition.getBeginTime()+"' and create_time<='"+condition.getEndTime()+"' ";

        if(!page.isDisabled() && !page.isNotCount()) {
            page.setCount(countOrder(conditionSql,pending));
        }

        builder.append(sql).append(conditionSql).append(" order by id desc limit ?,?");

        try {
            List<WorkOrder> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setByte(1 ,pending.getStatus());
                    ps.setLong(2, (page.getPageNo() - 1) * page.getPageSize());
                    ps.setInt(3, page.getPageSize());
                }
            }, new WorkOrderRowMapper());
            return page.setList(list);
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return page;
        }
    }

    /**
     * 根据条件统计总数
     * @param status
     * @return
     */
    private long countOrder(String conditionSql, OrderStatus status) {
        String sql = "select count(1) from t_work_order"+ getTableSuffix() +" where state = " + status.getStatus() + conditionSql;
        try {
            return getJdbcTemplate().queryForInt(sql);
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return 0;
        }
    }

    public class WorkOrderRowMapper implements RowMapper{

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            WorkOrder order = new WorkOrder();
            order.setId(rs.getLong("id"));
            order.setTitle(rs.getString("title"));
            order.setType(rs.getByte("type"));
            order.setDescri(rs.getString("descri"));
            order.setCreate_time(new Date(rs.getTimestamp("create_time").getTime()));
            order.setLevel(rs.getString("level"));
            order.setState(OrderStatus.valueOf(rs.getByte("state")));
            order.setAttachment_url(rs.getString("attachment_url"));
            order.setSuper_id(rs.getLong("super_id"));
            order.setAgent_id(rs.getLong("agent_id"));
            order.setGame_id(rs.getLong("game_id"));
            order.setRoleName(rs.getString("role_name"));
            order.setRoleNickName(rs.getString("role_nick_name"));
            order.setRoleId(rs.getLong("role_id"));
            order.setRoleType(rs.getByte("role_type"));

            order.setSatisfaction(rs.getInt("satisfaction"));
            order.setRemark(rs.getString("remark"));
            order.setAcceptUser(rs.getString("accept_user"));
            order.setAcceptTime(new Date(rs.getTimestamp("accept_time").getTime()));
            order.setFinal_actor_user(rs.getString("final_actor_user"));
            order.setFinal_actor_time(new Date(rs.getTimestamp("final_actor_time").getTime()));
            order.setQuestionType(rs.getByte("question_type"));

            return order;
        }
    }
}
