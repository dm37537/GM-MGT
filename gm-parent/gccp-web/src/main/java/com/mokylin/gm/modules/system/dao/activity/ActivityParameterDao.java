package com.mokylin.gm.modules.system.dao.activity;

import com.google.common.collect.Lists;
import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.ParameterType;
import com.mokylin.gm.modules.system.entity.activity.ActivityParameter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by Administrator on 2014/7/7.
 */
@Repository
public class ActivityParameterDao extends BaseGMDao {

    /**
     * 根据activityId 查找Parameter
     * @param activityId
     * @return
     */
    public List<ActivityParameter> findParameterByActivityId(final Integer activityId) {
        String sql = "select activity_id, param_id, game_key, param_key, param_label, param_type, param_require, param_min, param_max, param_version, param_hasSite, param_remark, param_premiseKey from t_sys_activity_param where activity_id= ? order by param_id ";

        try {
            return getJdbcTemplate().query(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, activityId);
                }
            }, new ParameterRowMapper());
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }

    /**
     * 根据paramId 查找Parameter
     * @param paramId
     * @return
     */
    public ActivityParameter findParameterByParamId(final Integer paramId) {
        String sql = "select activity_id, param_id, game_key, param_key, param_label, param_type, param_require, param_min, param_max, param_version, param_hasSite, param_remark, param_premiseKey from t_sys_activity_param where param_id= ? order by param_id ";

        try {
            return (ActivityParameter) getJdbcTemplate().queryForObject(sql, new Object[]{paramId}, new int[]{Types.INTEGER}, new ParameterRowMapper());
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 添加参数信息
     * @param parameter
     * @return
     */
    public boolean addActivityParameter(final ActivityParameter parameter) {
        String sql = "insert into t_sys_activity_param(activity_id, game_key, param_key, param_label, param_type, param_require, param_min, param_max, param_version, param_hasSite, param_remark, param_premiseKey) values(?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, parameter.getActivityId());
                    ps.setString(2, parameter.getGameKey());
                    ps.setString(3, parameter.getKey());
                    ps.setString(4, parameter.getLabel());
                    ps.setString(5, parameter.getType().name());
                    ps.setBoolean(6, parameter.isRequire());
                    ps.setString(7, parameter.getMin());
                    ps.setString(8, parameter.getMax());
                    ps.setString(9, parameter.getVersion());
                    ps.setString(10, parameter.getHasSite());
                    ps.setString(11, parameter.getRemark());
                    ps.setString(12, parameter.getPremiseKey());
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 修改参数信息
     * @param parameter
     * @return
     */
    public boolean updateActivityParameter(final ActivityParameter parameter) {
        String sql = "update t_sys_activity_param set game_key=?, param_key=?, param_label=?, param_type=?, param_require=?, param_min=?, param_max=?, param_version=?, param_hasSite=?, param_remark=?, param_premiseKey=? where param_id=? ";
        try {
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, parameter.getGameKey());
                    ps.setString(2, parameter.getKey());
                    ps.setString(3, parameter.getLabel());
                    ps.setString(4, parameter.getType().name());
                    ps.setBoolean(5, parameter.isRequire());
                    ps.setString(6, parameter.getMin());
                    ps.setString(7, parameter.getMax());
                    ps.setString(8, parameter.getVersion());
                    ps.setString(9, parameter.getHasSite());
                    ps.setString(10, parameter.getRemark());
                    ps.setString(11, parameter.getPremiseKey());
                    ps.setInt(12, parameter.getId());
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 根据activityId删除 参数
     * @param activityId
     * @return
     */
    public boolean deleteParameters(final Integer activityId) {
        String sql = "delete from t_sys_activity_param where activity_id = ? ";

        try {
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, activityId);
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    public boolean deleteParametersByParamId(final Integer paramId) {
        String sql = "delete from t_sys_activity_param where param_id = ? ";

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

    public class ParameterRowMapper implements RowMapper{

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ActivityParameter parameter = new ActivityParameter();

            parameter.setActivityId(rs.getInt("activity_id"));
            parameter.setId(rs.getInt("param_id"));
            parameter.setGameKey(rs.getString("game_key"));
            parameter.setKey(rs.getString("param_key"));
            parameter.setLabel(rs.getString("param_label"));
            parameter.setType(ParameterType.valueOf(rs.getString("param_type")));
            parameter.setRequire(rs.getBoolean("param_require"));
            parameter.setMin(rs.getString("param_min"));
            parameter.setMax(rs.getString("param_max"));
            parameter.setVersion(rs.getString("param_version"));
            parameter.setHasSite(rs.getString("param_hasSite"));
            parameter.setRemark(rs.getString("param_remark"));
            parameter.setPremiseKey(rs.getString("param_premiseKey"));

            return parameter;
        }
    }
}
