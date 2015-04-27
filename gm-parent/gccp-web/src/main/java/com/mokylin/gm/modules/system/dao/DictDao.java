package com.mokylin.gm.modules.system.dao;

import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.Dict;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.StringUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Date;

/**
 * 字典 DAO
 * Created by yongbo.chen
 * Time:2014/6/15 20:09
 */
@Repository
public class DictDao extends BaseGMDao {

    public List<Dict> findAllList() {
        String sql = "SELECT `id`, label, `value`, type, description, sort FROM t_sys_dict WHERE del_flag = " + Dict.DEL_FLAG_NORMAL + " ORDER BY type, sort";
        try{
            return getJdbcTemplate().query(sql, new DictRowMapper());
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public List<String> findTypeList() {
        String sql = "SELECT type FROM t_sys_dict WHERE del_flag = " + Dict.DEL_FLAG_NORMAL + " group by type";
        try{
            return getJdbcTemplate().query(sql, new RowMapper() {
                @Override
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("type");
                }
            });
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public Dict get(int id) {
        String sql = "SELECT `id`, label, `value`, type, description, sort FROM t_sys_dict WHERE del_flag = " + Dict.DEL_FLAG_NORMAL + " and id = ?";
        try {
            return (Dict)getJdbcTemplate().queryForObject(sql, new Object[]{id}, new int[]{Types.INTEGER}, new DictRowMapper());
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public boolean deleteById(final int id, final String userName) {
        String sql = "update t_sys_dict set del_flag = 1, update_by=?, update_date=? where id = ?";
        try {
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, userName);
                    ps.setTimestamp(2, new Timestamp(new Date().getTime()));
                    ps.setInt(3, id);
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 添加 | 修改
     * @param dict
     */
    public boolean update(final Dict dict, final String userName) {
        StringBuilder sql = new StringBuilder("replace t_sys_dict(`id`, label, `value`, type, description, sort, update_by, update_date) values(?, ?, ?, ?, ?, ?, ?, ?)");
        try {
            getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, dict.getId());
                    ps.setString(2, dict.getLabel());
                    ps.setString(3, dict.getValue());
                    ps.setString(4, dict.getType());
                    ps.setString(5, dict.getDescription());
                    ps.setInt(6, dict.getSort());
                    ps.setString(7, userName);
                    ps.setTimestamp(8, new Timestamp(new Date().getTime()));
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    public Page<Dict> findPage(final Page<Dict> page, Dict dict) {
        StringBuilder sql = new StringBuilder("SELECT `id`, label, `value`, type, description, sort FROM t_sys_dict WHERE del_flag = " + Dict.DEL_FLAG_NORMAL );
        StringBuilder sql1 = new StringBuilder();
        if(StringUtils.isNotBlank(dict.getType()))
            sql1.append(" and type ='"+dict.getType()+"' ");
        if(StringUtils.isNotBlank(dict.getDescription()))
            sql1.append(" and description like '%"+dict.getDescription()+"%'");

        if(!page.isDisabled() && !page.isNotCount()) {
            page.setCount(countDict(dict, sql1.toString()));
        }

        sql.append(sql1).append(" ORDER BY type, sort limit ?, ?");

        try {
            List<Dict> dicts = getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setLong(1, (page.getPageNo()-1) * page.getPageSize());
                    ps.setInt(2, page.getPageSize());
                }
            },new DictRowMapper());
            return page.setList(dicts);
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return page;
        }
    }

    /**
     * 根据条件统计字典数量
     */
    private int countDict(Dict dict, String sqlApp) {
        StringBuilder builder = new StringBuilder("select count(id) from t_sys_dict where del_flag= " + Dict.DEL_FLAG_NORMAL );
        try {
            return getJdbcTemplate().queryForInt(builder.append(sqlApp).toString());
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return 0;
        }
    }

    public class DictRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Dict dict = new Dict();
            dict.setId(rs.getInt("id"));
            dict.setLabel(rs.getString("label"));
            dict.setValue(rs.getString("value"));
            dict.setType(rs.getString("type"));
            dict.setDescription(rs.getString("description"));
            dict.setSort(rs.getInt("sort"));
            return dict;
        }
    }
}
