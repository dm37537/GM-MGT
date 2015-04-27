package com.mokylin.gm.modules.system.dao;

import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.dao.UserDao.UserRowMapper;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.utils.DateUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Date;

public class UserDao2 extends BaseGMDao{

	public User FindByUsername(String username)
	{
		String sql = "SELECT id, `username`, `password`, `name`, email, mobile, `status`, created_date, lastlogin_date, lastlogin_ip, lastmodify_date, description FROM t_sys_user WHERE `username` = ? AND `status` = 1 ";
		try{
            @SuppressWarnings("unchecked")
			User user = (User) getJdbcTemplate().queryForObject(sql, new Object[]{username}, new int[]{Types.VARCHAR}, new UserRowMapper());
            return user;
        } catch (Exception e) {
            return null;
        }    
		

	}
	
	
	 public class UserRowMapper implements RowMapper {

	        public Object mapRow(ResultSet rs, int index) throws SQLException {
	            User user = new User();
	            user.setId(rs.getLong("id"));
	            user.setUserName(rs.getString("username"));
	            user.setPassword(rs.getString("password"));
	            user.setName(rs.getString("name"));
	            user.setEmail(rs.getString("email"));
	            user.setMobile(rs.getString("mobile"));
	            user.setStatus(rs.getInt("status"));
	            user.setCreateDate(DateUtils.formatDateTime(rs.getTimestamp("created_date")));
	            user.setLastLoginDate(DateUtils.formatDateTime(rs.getTimestamp("lastlogin_date")));
	            user.setLastLoginIp(rs.getString("lastlogin_ip"));
	            user.setLastModifyDate(DateUtils.formatDateTime(rs.getTimestamp("lastmodify_date")));
	            user.setDescription(rs.getString("description"));

	            return user;
	        }
	    }
	
}
