package com.mokylin.gm.modules.system.dao;

import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.*;
import com.mokylin.gm.security.Digests;
import com.mokylin.gm.utils.DateUtils;
import com.mokylin.gm.utils.Encodes;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.StringUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.taglibs.standard.tag.common.core.ForEachSupport;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sun.security.provider.MD5;
import sun.text.resources.FormatData_in;

import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    public User findByUserId4All(Long userId) {
        String sql = "SELECT id, username, `password`, `name`, email, mobile, `status`, created_date, lastlogin_date, lastlogin_ip, lastmodify_date, description FROM t_sys_user WHERE `id` = ?";
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
    public User findByUserName(final String userName) {
        String sql = "SELECT id, `username`, `password`, `name`, email, mobile, `status`, created_date, lastlogin_date, lastlogin_ip, lastmodify_date, description FROM t_sys_user WHERE `username` = ? AND `status` = 1 ";
        try{
//            User user = (User) getJdbcTemplate().queryForObject(sql, new Object[]{userName}, new int[]{Types.VARCHAR}, new UserRowMapper());
//            return user;

            List<User> list = getJdbcTemplate().query(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1,userName);
                }
            }, new UserRowMapper());
            if(list.size()==1){
                return list.get(0);
            }else{
                return null;
            }

        } catch (Exception e) {
            LOGGER.error("执行查询Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 更新用户登录信息
     *
     * @param host
     * @param id
     */
    public void updateUserLoginInfo(final String host, final long id) {
        String sql = "UPDATE t_sys_user SET lastlogin_ip =?, lastlogin_date =? WHERE id =? ";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, host);
                    ps.setTimestamp(2, new Timestamp(new Date().getTime()));
                    ps.setLong(3, id);

                    ps.executeUpdate();
                }
            });
        } catch (Exception e) {
            LOGGER.error("update user exception:{}", e.getMessage(), e);
            return ;
        }
        return;
    }

    public boolean update(final User user) {
        String sql = "UPDATE t_sys_user set email = ?, mobile = ?,description=? where id = ? ";
        try {
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, user.getEmail());
                    ps.setString(2, user.getMobile());
                    ps.setString(3, user.getDescription());
                    ps.setLong(4, user.getId());
                }
            });
        } catch (Exception e) {
            LOGGER.error("update user exception:{}", e.getMessage(), e);
            return false;
        }
        return true;
    }

    public boolean updatePasswordById(final long id, final String newPassword) {
        String sql = "UPDATE t_sys_user set password = ? where id = ?";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1 , newPassword);
                    ps.setLong(2, id);
                }
            });
        } catch (Exception e){
            LOGGER.error("update user exception:{}", e.getMessage(), e);
            return false;
        }
        return true;
    }
    public Page<User> pageList(final Page<User> userPage, User user) {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_sys_user where 1=1 ");
        StringBuilder builder1 = new StringBuilder();
        if(null != user){
            if(StringUtils.isNotEmpty(user.getName()))
                builder1.append(" and name like '%" + user.getName() + "%'" );
            if(StringUtils.isNotEmpty(user.getUserName()))
                builder1.append(" and username like '%" + user.getUserName() + "%'" );
            if(StringUtils.isNotEmpty(user.getEmail()))
                builder1.append(" and email like '%" + user.getEmail() + "%'" );
            if(StringUtils.isNotEmpty(user.getMobile()))
                builder1.append(" and mobile like '%" + user.getMobile() + "%'" );

        }
        if(!userPage.isDisabled() && !userPage.isNotCount()){
            userPage.setCount(userCount(builder1.toString()));
        }
        builder.append(builder1).append(" limit ?, ?");

        try{
            List<User> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, (userPage.getPageNo()-1) * userPage.getPageSize());
                    ps.setInt(2, userPage.getPageSize());
                }
            }, new UserRowMapper());
            return userPage.setList(list);
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return userPage;
        }
    }
    public int userCount( String sqlApp){
        StringBuilder builder = new StringBuilder("select count(1) from t_sys_user where 1=1 ");
        try{
            return getJdbcTemplate().queryForInt(builder.append(sqlApp).toString());
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return 0;
        }
    }

    public boolean save(final User user) {

        String sql = "INSERT INTO `t_sys_user` (`username`, `description`, `name`, `email`, `mobile`,`password`) VALUES (?, ?, ?, ?,?,?)";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1,user.getUserName());
                    ps.setString(2,user.getDescription());
                    ps.setString(3,user.getName());
                    ps.setString(4,user.getEmail());
                    ps.setString(5,user.getMobile());
                    if(user.getPassword()!=null&&!user.getPassword().trim().equals("")){
                        ps.setString(6,  user.getPassword());
                    }else{
                        byte[] pwd = Digests.md5("123@mokylin".getBytes());
                        ps.setString(6,  Encodes.encodeHex(pwd));
                    }


                }
            });
            return true;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}",e.getMessage(),e);
        }
        return false;
    }

    public Page<User> findUserByRoleId(final Page<User> userPage,User user,final int roleId){
        StringBuilder builder = new StringBuilder ("SELECT * from t_sys_user u LEFT JOIN t_sys_user_role ur on u.id=ur.user_id WHERE ur.role_id="+roleId);
        StringBuilder builder1 = new StringBuilder();
        if(null != user){
            if(StringUtils.isNotEmpty(user.getName()))
                builder1.append(" and name like '%" + user.getName() + "%'" );
            if(StringUtils.isNotEmpty(user.getUserName()))
                builder1.append(" and username like '%" + user.getUserName() + "%'" );
            if(StringUtils.isNotEmpty(user.getEmail()))
                builder1.append(" and email like '%" + user.getEmail() + "%'" );
            if(StringUtils.isNotEmpty(user.getMobile()))
                builder1.append(" and mobile like '%" + user.getMobile() + "%'" );

        }
        if(!userPage.isDisabled() && !userPage.isNotCount()){
            userPage.setCount(userCount(builder1.toString()));
        }
        builder.append(builder1).append(" limit ?, ?");


        List<User> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, (userPage.getPageNo()-1) * userPage.getPageSize());
                ps.setInt(2, userPage.getPageSize());
            }
        }, new UserRowMapper());
        return userPage.setList(list);
    }

    public List<User> findUsersByUserNames(final String userNames) {
        String[] names=userNames.split(",");
        String inStr="";
        for (int i = 0; i <names.length ; i++) {
            if(i==0){
                inStr+="'"+names[i]+"'";
            }else{
                inStr+=",'"+names[i]+"'";
            }
        }
        StringBuilder builder = new StringBuilder ("select * from t_sys_user where username in ("+inStr+")");
        try{
            List list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {

                }
            }, new UserRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}",e.getMessage(),e);
        }
        return null;
    }

    public boolean updateStatus(final long userId,final int status) {
        String sql = "UPDATE t_sys_user set status = ? where id = ?";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, status);
                    ps.setLong(2, userId);
                }
            });
        } catch (Exception e){
            LOGGER.error("修改用户状态 exception:{}", e.getMessage(), e);
            return false;
        }
        return true;
    }

    public boolean resetPsw(final int userId) {
        String sql = "UPDATE t_sys_user set password = ? where id = ?";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    byte[] pwd = Digests.md5("123@mokylin".getBytes());
                    ps.setString(1,  Encodes.encodeHex(pwd));
                    ps.setInt(2, userId);
                }
            });
        } catch (Exception e){
            LOGGER.error("重置用户密码 exception:{}", e.getMessage(), e);
            return false;
        }
        return true;
    }

    public List<User_config> findConfigByUserId(final long useId) {
        String sql="select * from t_sys_user_config where user_id=?";
        try{
            return getJdbcTemplate().query(sql,new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setLong(1,useId);
                }
            },new UserConfigRowMapper());
        }catch (Exception e){
            LOGGER.error("获取角色权限Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public User_config findConfig(final int user_id,final int config_item_id) {
        String sql = "SELECT *  FROM t_sys_user_config WHERE `user_id` = ? and `config_item_id` = ? ";
        try{
            List<User_config> configs=getJdbcTemplate().query(sql,new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1,user_id);
                    ps.setInt(2,config_item_id);
                }
            },new UserConfigRowMapper());
            if(configs.size()==1){
                return  configs.get(0);
            }else{
                return null;
            }

        } catch (Exception e) {
            LOGGER.error("执行查询Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public boolean addConfig(final int user_id, final int config_item_id, final String config_item_value, final int type,final int sysId) {
        String sql="replace into  `t_sys_user_config` (`user_id`, `config_item_id`,`config_item_value`,`type`,`sys_id`) VALUES (?,?,?,?,?)";
        try {
            getJdbcTemplate().update(sql,new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1,user_id);
                    ps.setInt(2,config_item_id);
                    ps.setString(3,config_item_value);
                    ps.setInt(4,type);
                    ps.setInt(5,sysId);
                }
            });
            return true;
        }catch (Exception e){
            LOGGER.error("更新用户权限值Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    public boolean addConfigs(final int userId, final String pmsItemIds,final int sysId) {

        final List<String> pmsItemIdList= Arrays.asList(pmsItemIds.split(","));
        try {
            //删除所有值为 1的 开关权限
            getJdbcTemplate().update("delete from t_sys_user_config where user_id=? and type='1' and sys_id=?",new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1,userId);
                    ps.setInt(2,sysId);
                }
            });
            getJdbcTemplate().batchUpdate("replace into  `t_sys_user_config` (`user_id`, `config_item_id`,`config_item_value`,`type`,`sys_id`) VALUES (?,?,?,?,?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1,userId);
                    ps.setInt(2, Integer.parseInt(pmsItemIdList.get(i)));
                    ps.setString(3,"1");
                    ps.setInt(4,1);
                    ps.setInt(5,sysId);
                }

                @Override
                public int getBatchSize() {
                    return pmsItemIdList.size();
                }
            });
            return true;
        }catch (Exception e){
            LOGGER.error("添加用户权限配置Exception:{}", e.getMessage(), e);
            return false;
        }
    }

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

    public class UserConfigRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int index) throws SQLException {
            User_config config=new User_config();
            config.setConfig_item_id(rs.getInt("config_item_id"));
            config.setConfig_item_value(rs.getString("config_item_value"));
            config.setId(rs.getInt("id"));
            config.setUser_config_created_date(DateUtils.formatDateTime(rs.getTimestamp("user_config_created_date")));
            config.setUser_id(rs.getInt("user_id"));
            config.setSys_id(rs.getInt("sys_id"));
            config.setType(rs.getInt("type"));
            return config;
        }
    }
    

    
}

