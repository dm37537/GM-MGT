package com.mokylin.gm.modules.system.dao.action;

import com.google.common.collect.Lists;
import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.action.ActionParameterValue;
import com.mokylin.gm.modules.system.entity.action.Parameter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 查找集合类型 参数值
 * Created by Administrator on 2014/7/9.
 */
@Repository
public class ActionParameterListDao extends BaseGMDao {

    /**
     * 查找集合类型 参数值
     */
    public List<ActionParameterValue> findParameterValue(final int paramId) {
        String sql = "select list_label, list_value from t_sys_action_parameterlist where param_id = ? order by list_id";

        try {
            return getJdbcTemplate().query(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, paramId);
                }
            }, new ParameterValueRowMapper());
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }

    /**
     * 添加参数值
     * @param parameter
     * @return
     */
    public boolean saveActionParameterValues(final Parameter parameter) {
        String sql = "insert into t_sys_action_parameterlist(list_label, list_value, param_id, param_key) values(?, ?, ?, ?)";
        try {
            getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, parameter.getValues().get(i).getLabel());
                    ps.setString(2, parameter.getValues().get(i).getValue());
                    ps.setInt(3, parameter.getId());
                    ps.setString(4, parameter.getKey());
                }

                @Override
                public int getBatchSize() {
                    return parameter.getValues().size();
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    //删除参数值
    public boolean deleteParameterValue(final List<Parameter> colParamList) {
        String del_sql = "delete from t_sys_action_parameterlist where param_Id =? ";
        try {
            getJdbcTemplate().batchUpdate(del_sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, colParamList.get(i).getId());
                }

                @Override
                public int getBatchSize() {
                    return colParamList.size();
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    public class ParameterValueRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ActionParameterValue value = new ActionParameterValue();
            value.setLabel(rs.getString("list_label"));
            value.setValue(rs.getString("list_value"));

            return value;
        }
    }
}
