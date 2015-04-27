package com.mokylin.gm.modules.system.dao.action;

import com.google.common.collect.Lists;
import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.action.Action;
import com.mokylin.gm.modules.system.entity.action.Parameter;
import com.mokylin.gm.modules.system.entity.ParameterType;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by Administrator on 2014/7/3.
 */
@Repository
public class ActionParameterDao extends BaseGMDao{

    /**
     * 根据actionId加载Parameter
     * @return
     */
    public List<Parameter> findParametersByActionId(final Integer actionId) {
        String sql = "SELECT id, action_id, `key`, label, `type`, `require`, `min`, `max`, version, has_site, remark from t_sys_action_parameter where action_id = ? order by id";
        try {
            return getJdbcTemplate().query(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, actionId);
                }
            }, new ParameterRowMapper());
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }

    /**
     * 根据actionId加载Parameter
     * @return
     */
    public Parameter findParametersByParamId(final Integer paramId) {
        String sql = "SELECT id, action_id, `key`, label, `type`, `require`, `min`, `max`, version, has_site, remark from t_sys_action_parameter where id = ? order by id";
        try {
            return getJdbcTemplate().queryForObject(sql, new Object[]{paramId}, new int[]{Types.INTEGER}, new ParameterRowMapper());
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public boolean addParameter(final Parameter parameter) {
        String sql = "insert into t_sys_action_parameter(action_id, `key`, label, `type`, `require`, `min`, `max`, version, has_site, remark) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, parameter.getActionId());
                    ps.setString(2, parameter.getKey());
                    ps.setString(3, parameter.getLabel());
                    ps.setString(4, parameter.getType().name());
                    ps.setBoolean(5, parameter.isRequire());
                    ps.setString(6, parameter.getMin());
                    ps.setString(7, parameter.getMax());
                    ps.setString(8, parameter.getVersion());
                    ps.setString(9, parameter.getHasSite());
                    ps.setString(10, parameter.getRemark());
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 修改参数
     * @return
     */
    public boolean updateActionParameter(final Parameter parameter) {
        String sql = "update t_sys_action_parameter set `key`=?, label=?, `type`=?, `require`=?, `min`=?, `max`=?, version=?, has_site=?, remark=? where id=?";
        try {
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, parameter.getKey());
                    ps.setString(2, parameter.getLabel());
                    ps.setString(3, parameter.getType().name());
                    ps.setBoolean(4, parameter.isRequire());
                    ps.setString(5, parameter.getMin());
                    ps.setString(6, parameter.getMax());
                    ps.setString(7, parameter.getVersion());
                    ps.setString(8, parameter.getHasSite());
                    ps.setString(9, parameter.getRemark());
                    ps.setInt(10, parameter.getId());
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    public boolean deleteParameters(int actionId){
        String sql = "delete from t_sys_action_parameter where action_id = ? ";
        try {
            getJdbcTemplate().update(sql, new Object[]{actionId}, new int[]{Types.INTEGER});
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    public boolean deleteParametersByParamId(final Integer paramId) {
        String sql = "delete from t_sys_action_parameter where id = ? ";
        try {
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, paramId);
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }


    public class ParameterRowMapper implements RowMapper<Parameter> {

        @Override
        public Parameter mapRow(ResultSet rs, int rowNum) throws SQLException {
            Parameter parameter = new Parameter();
            parameter.setId(rs.getInt("id"));
            parameter.setActionId(rs.getInt("action_id"));
            parameter.setKey(rs.getString("key"));
            parameter.setLabel(rs.getString("label"));
            parameter.setType(ParameterType.valueOf( rs.getString("type")));
            parameter.setRequire(rs.getBoolean("require"));
            parameter.setMin(rs.getString("min"));
            parameter.setMax(rs.getString("max"));
            parameter.setVersion(rs.getString("version"));
            parameter.setHasSite(rs.getString("has_site"));
            parameter.setRemark(rs.getString("remark"));
            return parameter;
        }
    }
}
