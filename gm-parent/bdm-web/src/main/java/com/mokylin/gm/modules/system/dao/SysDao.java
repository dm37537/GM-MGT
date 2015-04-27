package com.mokylin.gm.modules.system.dao;

import com.google.common.collect.Lists;
import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.Role;
import com.mokylin.gm.modules.system.entity.Sys;
import com.mokylin.gm.utils.DateUtils;
import com.mokylin.gm.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Repository
public class SysDao extends BaseGMDao {
    /**
     * 系统分页列表
     * @param page
     * @param sys
     * @return
     */
    public Page<Sys> pageList(final Page<Sys> page, Sys sys) {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_sys where 1=1 and system_state!=-1 ");
        StringBuilder builder1 = new StringBuilder();
        if(null != sys){
            if(sys.getId()!=null&&sys.getId()!=0)
                builder1.append(" and id=" +sys.getId()+"");
            if(StringUtils.isNotEmpty(sys.getSystem_name()))
                builder1.append(" and system_name like '%" + sys.getSystem_name() + "%'" );
        }
        if(!page.isDisabled() && !page.isNotCount()){
            page.setCount(sysCount(builder1.toString()));
        }
        builder.append(builder1).append(" order by system_sequence ");
        builder.append(builder1).append(" limit ?, ?");

        try{
            List<Sys> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, (page.getPageNo()-1) * page.getPageSize());
                    ps.setInt(2, page.getPageSize());
                }
            }, new SysRowMapper());
            return page.setList(list);
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return page;
        }
    }

    /**
     * 系统数据总数
     * @param sqlApp
     * @return
     */
    public int sysCount( String sqlApp){
        StringBuilder builder = new StringBuilder("select count(1) from t_sys where 1=1 ");
        try{
            return getJdbcTemplate().queryForInt(builder.append(sqlApp).toString());
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return 0;
        }
    }
    public Sys findSysById(final int id){
        String sql = "SELECT * from   t_sys WHERE `id` = ?";
        try{
            List<Sys> list = getJdbcTemplate().query(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1,id);
                }
            }, new SysRowMapper());
            if(list.size()==1){
                return list.get(0);
            }else {
                return  null;
            }
        } catch (Exception e) {
            LOGGER.error("执行查询Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public boolean updateSysById(final Sys sys) {
        String sql = "UPDATE t_sys set  system_name=? ,  system_sequence=?,ip_list=?,verify_code=?  where id = ?";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, sys.getSystem_name());
                    ps.setInt(2, sys.getSystem_sequence());
                    ps.setString(3,sys.getIp_list());
                    ps.setString(4,sys.getVerify_code());
                    ps.setInt(5, sys.getId());
                }
            });
        } catch (Exception e){
            LOGGER.error("update user exception:{}", e.getMessage(), e);
            return false;
        }
        return true;
    }

    public boolean deleteSysById(final int id) {
        String sql = "UPDATE t_sys set  system_state=-1  where id = ?";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, id);
                }
            });
        } catch (Exception e){
            LOGGER.error("update user exception:{}", e.getMessage(), e);
            return false;
        }
        return true;
    }

    public boolean save(final Sys sys) {
        String sql = "INSERT INTO `t_sys` (`system_type`, `system_name`, `system_sequence`,`ip_list`,`verify_code`) VALUES (?,?, ?,?,?)";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setByte(1, sys.getSystem_type());
                    ps.setString(2,sys.getSystem_name());
                    ps.setInt(3, sys.getSystem_sequence());
                    ps.setString(4,sys.getIp_list());
                    ps.setString(5,sys.getVerify_code());
                }
            });
            return true;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}",e.getMessage(),e);
        }
        return false;
    }

    public List<Sys> listAll() {
        List<Sys> list= Lists.newArrayList();
        StringBuilder builder = new StringBuilder("SELECT * FROM t_sys where 1=1 and system_state!=-1 ");
        try{
            list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {

                }
            }, new SysRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return list;
        }
    }

    public class SysRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int index) throws SQLException {
            Sys sys=new Sys();
            sys.setId(rs.getInt("id"));
            sys.setSystem_created_date(DateUtils.formatDateTime(rs.getTimestamp("system_created_date")));
            sys.setSystem_name(rs.getString("system_name"));
            sys.setSystem_sequence(rs.getInt("system_sequence"));
            sys.setSystem_type(rs.getByte("system_type"));
            sys.setIp_list(rs.getString("ip_list"));
            sys.setVerify_code(rs.getString("verify_code"));
            return sys;
        }
    }
}
