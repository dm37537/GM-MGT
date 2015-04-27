package com.mokylin.gm.modules.system.dao.activity;

import com.google.common.collect.Lists;
import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.activity.ActivityCondition;
import com.mokylin.gm.modules.system.entity.activity.ActivityInfo;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

/**
 * 活动配置  活动信息dao
 * Created by Administrator on 2014/7/7.
 */
@Repository
public class ActivityInfoDao extends BaseGMDao{

    public List<ActivityInfo> findAllList(ActivityCondition condition) {
        StringBuilder sql = new StringBuilder("SELECT t_sys_activity_info.activity_id, t_sys_activity_info.activity_parentType, t_sys_activity_info.activity_type, t_sys_activity_info.activity_name, t_sys_activity_info.activity_gameId,"
                + " t_sys_activity_info.activity_gameName,t_sys_activity_info.activity_gameVersion,t_sys_activity_info.activity_hasSite,t_sys_activity_info.activity_remark,t_sys_activity_info.activity_url"
                + " FROM t_sys_activity_info where 1=1 ");
        StringBuilder sqlOrder = new StringBuilder(" ORDER BY t_sys_activity_info.activity_gameId, t_sys_activity_info.activity_parentType, t_sys_activity_info.activity_type ");
        if(condition.getGameId() != 0) {
            sql.append(" and t_sys_activity_info.activity_gameId=" + condition.getGameId());
        }
        sql.append(sqlOrder);
        try {
            return getJdbcTemplate().query(sql.toString(), new ActivityInfoRowMapper());
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }

    /**
     * 根据activityInfo的id查找 ActivityInfo
     * @param activityInfo
     * @return
     */
    public ActivityInfo findActivityInfo(ActivityInfo activityInfo) {
        String sql = "SELECT t_sys_activity_info.activity_id, t_sys_activity_info.activity_parentType, t_sys_activity_info.activity_type, t_sys_activity_info.activity_name, t_sys_activity_info.activity_gameId,"
                + " t_sys_activity_info.activity_gameName,t_sys_activity_info.activity_gameVersion,t_sys_activity_info.activity_hasSite,t_sys_activity_info.activity_remark,t_sys_activity_info.activity_url"
                + " FROM t_sys_activity_info where  activity_id = ? ";
        try {
            return (ActivityInfo)getJdbcTemplate().queryForObject(sql, new Object[]{ activityInfo.getId()}, new int[]{Types.INTEGER}, new ActivityInfoRowMapper());
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return activityInfo;
        }
    }

    public int addActivityInfo(final ActivityInfo activityInfo) {
        final String sql = "insert into t_sys_activity_info(activity_parentType, activity_type, activity_name, activity_gameId, activity_gameName, activity_gameVersion, activity_hasSite, activity_remark, activity_url) values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            getJdbcTemplate().update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    ps.setInt(1, activityInfo.getParentType());
                    ps.setInt(2, activityInfo.getType());
                    ps.setString(3, activityInfo.getName());
                    ps.setInt(4, activityInfo.getGameId());
                    ps.setString(5, activityInfo.getGameName());
                    ps.setString(6, activityInfo.getGameVersion());
                    ps.setString(7, activityInfo.getHasSite());
                    ps.setString(8, activityInfo.getRemark());
                    ps.setString(9, activityInfo.getUrl());
                    return ps;
                }
            }, keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 修改activityInfo
     * @param activityInfo
     * @return
     */
    public boolean updateActivityInfo(final ActivityInfo activityInfo) {
        String sql = "update t_sys_activity_info set activity_parentType=?, activity_type=?, activity_name=?, activity_gameId=?, activity_gameName=?, activity_gameVersion=?, activity_hasSite=?, activity_remark=?, activity_url=? where activity_id=? ";
        try {
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, activityInfo.getParentType());
                    ps.setInt(2, activityInfo.getType());
                    ps.setString(3, activityInfo.getName());
                    ps.setInt(4, activityInfo.getGameId());
                    ps.setString(5, activityInfo.getGameName());
                    ps.setString(6, activityInfo.getGameVersion());
                    ps.setString(7, activityInfo.getHasSite());
                    ps.setString(8, activityInfo.getRemark());
                    ps.setString(9, activityInfo.getUrl());
                    ps.setInt(10, activityInfo.getId());
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 删除ActivityInfo
     * @param activityId
     * @return
     */
    public boolean deleteActivityInfo(final Integer activityId) {
        String sql = "delete from t_sys_activity_info where activity_id = ? ";

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

    public class ActivityInfoRowMapper implements RowMapper{

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ActivityInfo info = new ActivityInfo();

            info.setId(rs.getInt("activity_id"));
            info.setParentType(rs.getInt("activity_parentType"));
            info.setType(rs.getInt("activity_type"));
            info.setName(rs.getString("activity_name"));
            info.setGameId(rs.getInt("activity_gameId"));
            info.setGameName(rs.getString("activity_gameName"));
            info.setGameVersion(rs.getString("activity_gameVersion"));
            info.setHasSite(rs.getString("activity_hasSite"));
            info.setRemark(rs.getString("activity_remark"));
            info.setUrl(rs.getString("activity_url"));

            return info;
        }
    }
}
