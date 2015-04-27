package com.mokylin.gm.modules.system.dao;

import com.google.common.collect.Lists;
import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.Role;
import com.mokylin.gm.modules.system.entity.Role_config;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.utils.DateUtils;
import com.mokylin.gm.utils.Page;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Objects;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Repository
public class RoleConfigDao extends BaseGMDao {
    public List<Role_config> findConfigByRoleId(final int roleId){
        String sql="select * from t_sys_role_config where role_id=?";
        try{
            return getJdbcTemplate().query(sql,new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1,roleId);
                }
            },new RoleConfigRowMapper());
        }catch (Exception e){
            LOGGER.error("获取角色权限Exception:{}", e.getMessage(), e);
            return null;
        }

    }

    public Role_config findConfig(final int role_id, final int config_item_id) {
        String sql = "SELECT *  FROM t_sys_role_config WHERE `role_id` = ? and `config_item_id` = ? ";
        try{
            List<Role_config> configs=getJdbcTemplate().query(sql,new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1,role_id);
                    ps.setInt(2,config_item_id);
                }
            },new RoleConfigRowMapper());
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

//    public boolean addConfig4RoleTyp2(final int role_id, final int config_item_id, final String config_item_value) {
//        String sql="replace into  `t_sys_role_config` (`role_id`, `config_item_id`,`config_item_value`,`type`) VALUES (?,?,?,?)";
//        try {
//            getJdbcTemplate().update(sql,new PreparedStatementSetter() {
//                @Override
//                public void setValues(PreparedStatement ps) throws SQLException {
//                    ps.setInt(1,role_id);
//                    ps.setInt(2,config_item_id);
//                    ps.setString(3,config_item_value);
//                    ps.setInt(4,2);
//                }
//            });
//            return true;
//        }catch (Exception e){
//            LOGGER.error("更新角色权限值Exception:{}", e.getMessage(), e);
//            return false;
//        }
//    }

    public boolean addConfig(final int role_id,final  int config_item_id,final  String config_item_value,final  int type) {
        String sql="replace into  `t_sys_role_config` (`role_id`, `config_item_id`,`config_item_value`,`type`) VALUES (?,?,?,?)";
        try {
            getJdbcTemplate().update(sql,new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1,role_id);
                    ps.setInt(2,config_item_id);
                    ps.setString(3,config_item_value);
                    ps.setInt(4,type);
                }
            });
            return true;
        }catch (Exception e){
            LOGGER.error("更新角色权限值Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    public class RoleConfigRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int index) throws SQLException {
            Role_config config=new Role_config();
            config.setConfig_item_id(rs.getInt("config_item_id"));
            config.setConfig_item_value(rs.getString("config_item_value"));
            config.setId(rs.getInt("id"));
            config.setRole_config_created_date(DateUtils.formatDateTime(rs.getTimestamp("role_config_created_date")));
            config.setRole_id(rs.getInt("role_id"));
            config.setType(rs.getInt("type"));
            return config;
        }
    }
}
