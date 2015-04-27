package com.mokylin.gm.modules.system.dao;

import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.Dict;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 字典 DAO
 * Created by yongbo.chen
 * Time:2014/6/15 20:09
 */
@Repository
public class DictDao extends BaseGMDao {

    public List<Dict> findAllList() {
        String sql = "SELECT label, `value`, type, description, sort FROM t_sys_dict WHERE del_flag = " + Dict.DEL_FLAG_NORMAL + " ORDER BY sort";
        return getJdbcTemplate().query(sql, new DictRowMapper());
    }

    public List<String> findTypeList() {
        String sql = "SELECT type FROM t_sys_dict WHERE del_flag = " + Dict.DEL_FLAG_NORMAL + " group by type";
        return getJdbcTemplate().query(sql, new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("type");
            }
        });
    }

    public class DictRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Dict dict = new Dict();
            dict.setLabel(rs.getString("label"));
            dict.setValue(rs.getString("value"));
            dict.setType(rs.getString("type"));
            dict.setDescription(rs.getString("description"));
            dict.setSort(rs.getInt("sort"));
            return dict;
        }
    }
}
