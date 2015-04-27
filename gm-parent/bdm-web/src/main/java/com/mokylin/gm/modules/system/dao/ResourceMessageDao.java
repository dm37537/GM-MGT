package com.mokylin.gm.modules.system.dao;

import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.ResourceMessage;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.StringUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 国际化资源 dao
 * Created by Administrator on 2014/6/19.
 */
@Repository
public class ResourceMessageDao extends BaseGMDao{

    public List<ResourceMessage> loadResourceBundle() {
        String sql = "SELECT `key`, `value`, locale, bundle, `comment` FROM t_resource_bundle;";
        try{
            List<ResourceMessage> list = getJdbcTemplate().query(sql, new ResourceMessageRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("执行查询Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 分页查询 国际化记录
     * @param page
     * @param resource
     * @return
     */
    public Page<ResourceMessage> findResourceMessage(final Page<ResourceMessage> page, ResourceMessage resource) {
        StringBuilder builder = new StringBuilder("SELECT `key`, `value`, locale, bundle, `comment` FROM t_resource_bundle where 1=1 ");
        StringBuilder builder1 = new StringBuilder();
        if(null != resource){
            if(StringUtils.isNotEmpty(resource.getLocale()))
                builder1.append(" and locale like '%" + resource.getLocale() + "%'" );
            if(StringUtils.isNotEmpty(resource.getBundle()))
                builder1.append(" and bundle like '%" + resource.getBundle() + "%'" );
            if(StringUtils.isNotEmpty(resource.getKey()))
                builder1.append(" and `key` like '%" + resource.getKey() + "%'" );
            if(StringUtils.isNotEmpty(resource.getValue()))
                builder1.append(" and `value` like '%" + resource.getValue() + "%' " );
        }
        if(!page.isDisabled() && !page.isNotCount()){
            page.setCount(countResourceMessage(builder1.toString()));
        }
        builder.append(builder1).append(" limit ?, ?");

        try{
            List<ResourceMessage> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, (page.getPageNo()-1) * page.getPageSize());
                    ps.setInt(2, page.getPageSize());
                }
            }, new ResourceMessageRowMapper());
            return page.setList(list);
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return page;
        }
    }

    /**
     * 根据条件统计国际化数量
     * @param sqlApp
     * @return
     */
    public int countResourceMessage( String sqlApp) {
        StringBuilder builder = new StringBuilder("select count(1) from t_resource_bundle where 1=1 ");
        try{
            return getJdbcTemplate().queryForInt(builder.append(sqlApp).toString());
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 删除国际化信息
     * @param locale
     * @param bundle
     * @param key
     * @return
     */
    public boolean deleteResourceMessage(final String locale, final String bundle, final String key) {
        String sql = "DELETE FROM `t_resource_bundle` WHERE `key`=? AND `locale`=? AND `bundle`=? ";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, key);
                    ps.setString(2, locale);
                    ps.setString(3, bundle);
                }
            });
            return true;
        } catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    public class ResourceMessageRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int index) throws SQLException {
            ResourceMessage message = new ResourceMessage();
            message.setKey(rs.getString("key"));
            message.setValue(rs.getString("value"));
            message.setBundle(rs.getString("bundle"));
            message.setLocale(rs.getString("locale"));

            return message;
        }
    }
}
