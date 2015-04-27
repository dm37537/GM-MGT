package com.mokylin.gm.modules.system.dao;

import com.google.common.collect.Lists;
import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.Role;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.utils.DateUtils;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.servlet.jsp.tagext.TryCatchFinally;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Repository
public class RoleDao extends BaseGMDao {

    public Page<Role> pageList(final Page<Role> page, Role role) {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_sys_role where 1=1 ");
        StringBuilder builder1 = new StringBuilder();
        if(null != role){
            if(role.getSys_id()!=0)
                builder1.append(" and sys_id = " + role.getSys_id() + "" );
        }
        if(!page.isDisabled() && !page.isNotCount()){
            page.setCount(roleCount(builder1.toString()));
        }
        builder.append(builder1).append(" limit ?, ?");

        try{
            List<Role> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, (page.getPageNo()-1) * page.getPageSize());
                    ps.setInt(2, page.getPageSize());
                }
            }, new RoleRowMapper());
            return page.setList(list);
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return page;
        }
    }
    public int roleCount( String sqlApp){
        StringBuilder builder = new StringBuilder("select count(1) from t_sys_role where 1=1 ");
        try{
            return getJdbcTemplate().queryForInt(builder.append(sqlApp).toString());
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return 0;
        }
    }

    public Role findById(int roleId) {
        String sql = "SELECT *  FROM t_sys_role WHERE `id` = ?";
        try{
            Role role = (Role) getJdbcTemplate().queryForObject(sql, new Object[]{roleId}, new int[]{Types.BIGINT}, new RoleRowMapper());
            return role;
        } catch (Exception e) {
            LOGGER.error("执行查询Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public boolean addUsers(final List<User> users,final int roleId) {
        try{
                getJdbcTemplate().batchUpdate("replace into  `t_sys_user_role` (`user_id`, `role_id`) VALUES (?, ?)",new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, users.get(i).getId());
                        ps.setInt(2, roleId);
                    }
                    @Override
                    public int getBatchSize() {
                        return users.size();
                    }
                });
            return true;
        } catch (Exception e) {
            LOGGER.error("添加角色成员Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    public boolean delUser(final int userId, final int roleId) {
        String sql="delete from `t_sys_user_role` where role_id=? and user_id=?";
        try {
            getJdbcTemplate().update(sql,new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1,roleId);
                    ps.setInt(2,userId);
                }
            });
            return true;
        }catch (Exception e){
            LOGGER.error("删除角色成员Exception:{}", e.getMessage(), e);
            return false;
        }


    }

    public List<Role> listBySysId(final int sysId) {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_sys_role where 1=1 ");
        builder.append(" and sys_id = ?" );
        builder.append(" order by del_flag");
        try{
            List<Role> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1,sysId);
                }
            }, new RoleRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }

    public boolean addRole(final String roleName,final int sysId) {
       final  User user = (User) UserUtils.getCache(CACHE_USER);
        String sql="INSERT INTO `t_sys_role` (`name`, `create_by`, `update_by`, `remark`, `sys_id`) VALUES (?, ?, ?, ?, ?)";
        try {
            getJdbcTemplate().update(sql,new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1,roleName);
                    ps.setLong(2, user.getId());
                    ps.setLong(3,user.getId());
                    ps.setString(4,roleName);
                    ps.setInt(5,sysId);
                }
            });
            return true;
        }catch (Exception e){
            LOGGER.error("添加角色Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    public boolean delRole(final int roleId) {
        String sql="UPDATE  `t_sys_role`SET del_flag=1 where id=? ";
        try {
            getJdbcTemplate().update(sql,new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1,roleId);
                }
            });
            return true;
        }catch (Exception e){
            LOGGER.error("删除角色Exception:{}", e.getMessage(), e);
            return false;
        }

    }

    public boolean addConfigs(final int roleId, String pmsItemIds) {
        final List<String> pmsItemIdList= Arrays.asList(pmsItemIds.split(","));
        try {
            //删除所有值为 1的 开关权限
            getJdbcTemplate().update("delete from t_sys_role_config where role_id=? and type='1'",new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1,roleId);
                }
            });
            getJdbcTemplate().batchUpdate("replace into  `t_sys_role_config` (`role_id`, `config_item_id`,`config_item_value`,`type`) VALUES (?,?,?,?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1,roleId);
                    ps.setInt(2, Integer.parseInt(pmsItemIdList.get(i)));
                    ps.setString(3,"1");
                    ps.setInt(4,1);
                }

                @Override
                public int getBatchSize() {
                    return pmsItemIdList.size();
                }
            });
            return true;
        }catch (Exception e){
            LOGGER.error("添加角色权限配置Exception:{}", e.getMessage(), e);
            return false;
        }
    }
    public List<Role> findAllRole(final long userId,final int sysId){
        StringBuilder builder = new StringBuilder ("SELECT * from t_sys_role u LEFT JOIN t_sys_user_role ur on u.id=ur.role_id WHERE ur.user_id=? and u.sys_id=?");

        List<Role> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, userId);
                ps.setInt(2,sysId);
            }
        }, new RoleRowMapper());
        return list;
    }

    public class RoleRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int index) throws SQLException {
            Role role = new Role();
            role.setId(rs.getInt("id"));
            role.setCreate_by(rs.getLong("create_by"));
            role.setCreate_date(DateUtils.formatDateTime(rs.getTimestamp("create_date")));
            role.setDel_flag(rs.getShort("del_flag"));
            role.setName(rs.getString("name"));
            role.setRemark(rs.getString("remark"));
            role.setUpdate_by(rs.getLong("update_by"));
            role.setUpdate_date(DateUtils.formatDateTime(rs.getTimestamp("update_date")));
            return role;
        }
    }
}
