package com.mokylin.gm.modules.system.dao;

import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.dao.DictDao.DictRowMapper;
import com.mokylin.gm.modules.system.entity.Dict;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.utils.DateUtils;

import org.apache.shiro.SecurityUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Date;

/**
 * Created by yongbo.chen
 * Time:2014/6/13 12:22
 */
@Repository
public class UserDao extends BaseGMDao {

    /**
     * 根据用户Id查询用户信息
     *
     * @param userId
     * @return
     */
    public User findByUserId(Long userId) {
        String sql = "SELECT id, username, `password`, `name`, email, mobile, `status`, created_date, lastlogin_date, lastlogin_ip, lastmodify_date, description FROM t_sys_user WHERE `id` = ? AND `status` = 1 ";
        try{
            User user = (User) getJdbcTemplate().queryForObject(sql, new Object[]{userId}, new int[]{Types.BIGINT}, new UserRowMapper());
            return user;
        } catch (Exception e) {
            LOGGER.error("执行查询Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param userName
     * @return
     */
    public User findByUserName(String userName) {
        String sql = "SELECT id, `username`, `password`, `name`, email, mobile, `status`, created_date, lastlogin_date, lastlogin_ip, lastmodify_date, description FROM t_sys_user WHERE `username` = ? AND `status` = 1 ";
        try{
            User user = (User) getJdbcTemplate().queryForObject(sql, new Object[]{userName}, new int[]{Types.VARCHAR}, new UserRowMapper());
            return user;
        } catch (Exception e) {
            LOGGER.error("执行查询Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 记录用户登录记录
     */
    public void saveUserLoginInfo(final String host, final String userName) {
        String sql = "insert into t_sys_loginrecord(`username`, `host`, `logindate`) values(?, ?, ?)";

        try {
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, userName);
                    ps.setString(2, host);
                    ps.setTimestamp(3, new Timestamp(new Date().getTime()));
                }
            });
        } catch (DataAccessException e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
        }
    }
    
    
    
    

//    /**
//     * 更新用户登录信息
//     *
//     * @param host
//     * @param userName
//     */
//    public void updateUserLoginInfo(final String host, final String userName) {
//        String sql = "UPDATE t_sys_user SET lastlogin_ip =?, lastlogin_date =? WHERE `username` =? ";
//        try{
//            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
//                @Override
//                public void setValues(PreparedStatement ps) throws SQLException {
//                    ps.setString(1, host);
//                    ps.setTimestamp(2, new Timestamp(new Date().getTime()));
//                    ps.setString(3, userName);
//
//                    ps.executeUpdate();
//                }
//            });
//        } catch (Exception e) {
//            LOGGER.error("update user exception:{}", e.getMessage(), e);
//            return ;
//        }
//        return;
//    }

    public boolean save(final User user) {
        String sql = "UPDATE t_sys_user set email = ?, mobile = ? where id = ? ";
        try {
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, user.getEmail());
                    ps.setString(2, user.getMobile());
                    ps.setLong(3, user.getId());
                }
            });
        } catch (Exception e) {
            LOGGER.error("update user exception:{}", e.getMessage(), e);
            return false;
        }
        return true;
    }

//    public boolean updatePasswordById(final long id, final String newPassword) {
//        String sql = "UPDATE t_sys_user set password = ? where id = ?";
//        try{
//            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
//                @Override
//                public void setValues(PreparedStatement ps) throws SQLException {
//                    ps.setString(1 , newPassword);
//                    ps.setLong(2, id);
//                }
//            });
//        } catch (Exception e){
//            LOGGER.error("update user exception:{}", e.getMessage(), e);
//            return false;
//        }
//        return true;
//    }


    public class UserRowMapper implements RowMapper {

        @Override
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
    
    
    public void CreateNewUser(final String userName, final String passWord, final String Name, final String Email, final String Mobile) {
        String sql = "insert into t_sys_user(`id`, `username`, `password`, `name`, `email`, `mobile`, `status`, `created_date`, `lastlogin_date`, `lastlogin_ip`, `lastmodify_date`, `description`) values( ?, ?, ?,?, ?, ?,?,?,?,?,?,?)";

        try {
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                	System.out.println(" Create user ");
                	int id_total = countID();
                	ps.setInt(1, id_total+1);
                    ps.setString(2, userName);
                    ps.setString(3, passWord);
                    ps.setString(4, Name);
                    ps.setString(5, Email);
                    ps.setString(6, Mobile);
                    ps.setInt(7, 1);
                    ps.setTimestamp(8, getCurrentTimeStamp());
                    ps.setTimestamp(9, getCurrentTimeStamp());
                    ps.setString(10, SecurityUtils.getSubject().getSession().getHost());
                    ps.setTimestamp(11,  getCurrentTimeStamp());
                    ps.setString(12, "description");


                }
            });
        } catch (DataAccessException e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
        }
    }
    
    
    @SuppressWarnings("deprecation")
	public int countID() {
        String sql = "select count(id) from t_sys_user";
        try {
            return (int)getJdbcTemplate().queryForInt(sql);
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return 0;
        }
    }
    
    private static java.sql.Timestamp getCurrentTimeStamp() {

    	java.util.Date today = new java.util.Date();
    	return new java.sql.Timestamp(today.getTime());

    }

	public int CheckUser(String username) {
		String sql = "select count(*) from t_sys_user where username = '"+ username + "'";
        try {
            return (int)getJdbcTemplate().queryForInt(sql);
        } catch (Exception e) {
            //LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return 0;
        }
	}

}
