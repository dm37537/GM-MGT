package com.mokylin.gm.modules.system.dao;

import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.log.Log;
import com.mokylin.gm.modules.system.entity.log.LogCondition;
import com.mokylin.gm.utils.DateUtils;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.StringUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 请求Log DAO
 * Created by Administrator on 2014/6/26.
 */
@Repository
public class LogDao extends BaseGMDao{
    public void save(final Log log) {
        String sql = "INSERT INTO `t_sys_log` (`type`, `create_by`, `create_date`, `remote_addr`, `user_agent`, `request_uri`, `method`, `params`, `exception`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, log.getType());
                    ps.setString(2, log.getCreateBy());
                    ps.setTimestamp(3, new Timestamp(log.getCreateDate().getTime()));
                    ps.setString(4, log.getRemoteAddr());
                    ps.setString(5, log.getUserAgent());
                    ps.setString(6, log.getRequestUri());
                    ps.setString(7, log.getMethod());
                    ps.setString(8, log.getParams());
                    ps.setString(9, log.getException());
                }
            });
        }catch (Exception e){
            LOGGER.error("DB Exception:{}",e.getMessage(),e);
        }
    }

    /**
     * 根据条件查询日志
     * @param condition
     * @param logPage
     * @return
     */
    public Page<Log> query(final LogCondition condition, final Page<Log> logPage) {
        StringBuilder sql = new StringBuilder("select type, create_by, create_date, remote_addr, request_uri, method, params, exception from t_sys_log where create_date > '"+condition.getBeginTime()+" 00:00:00' and create_date<= '"+condition.getEndTime()+" 23:59:59' ");
        StringBuilder sql1= new StringBuilder();
        if(StringUtils.isNotBlank(condition.getUserName()))
            sql1.append(" and create_by='"+ condition.getUserName() +"' ");
        if(Log.TYPE_EXCEPTION.equals(condition.getType()))
            sql1.append(" and type = '"+ condition.getType()+"' ");

        if(!logPage.isDisabled() && !logPage.isNotCount()) {
            logPage.setCount(countLog(condition, sql1.toString()));
        }
        sql.append(sql1).append(" ORDER BY create_date DESC limit ?,?");

        try{
            List<Log> list = getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setLong(1, (logPage.getPageNo()-1) * logPage.getPageSize());
                    ps.setInt(2, logPage.getPageSize());
                }
            }, new RowMapper<Log>() {
                @Override
                public Log mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Log log = new Log();
                    log.setType(rs.getString("type"));
                    log.setCreateBy(rs.getString("create_by"));
                    log.setCreateDate(new Date(rs.getTimestamp("create_date").getTime()));
                    log.setRemoteAddr(rs.getString("remote_addr"));
                    log.setRequestUri(rs.getString("request_uri"));
                    log.setMethod(rs.getString("method"));
                    log.setParams(rs.getString("params"));
                    log.setException(rs.getString("exception"));
                    return log;
                }
            });
            return logPage.setList(list);
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return logPage;
        }
    }

    /**
     * 根据条件查询日志数量
     */
    public int countLog(LogCondition condition, String sqlApp) {
        StringBuilder builder = new StringBuilder("select count(*) from t_sys_log where create_date > '"+condition.getBeginTime()+" 00:00:00' and create_date<= '"+condition.getEndTime()+" 23:59:59' ");
        try{
            return getJdbcTemplate().queryForInt(builder.append(sqlApp).toString());
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return 0;
        }
    }


}
