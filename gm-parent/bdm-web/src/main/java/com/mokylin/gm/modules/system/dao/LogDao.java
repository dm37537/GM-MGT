package com.mokylin.gm.modules.system.dao;

import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.Log;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
}
