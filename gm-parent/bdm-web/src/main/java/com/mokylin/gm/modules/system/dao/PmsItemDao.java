package com.mokylin.gm.modules.system.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.Pms_item;
import com.mokylin.gm.utils.DateUtils;
import com.mokylin.gm.utils.Page;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Repository
public class PmsItemDao extends BaseGMDao {
    public Page<Pms_item> pageListBySysId(final Page<Pms_item> page, Pms_item pmsItem,final int sysId) {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_sys_pms_item where 1=1 and sys_id=? ");
        StringBuilder builder1 = new StringBuilder();
        if(null != pmsItem){
        }
        if(!page.isDisabled() && !page.isNotCount()){
            page.setCount(pmsCount(builder1.toString()));
        }
        builder.append(builder1).append(" limit ?, ?");

        try{
            List<Pms_item> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, sysId);
                    ps.setInt(2, (page.getPageNo() - 1) * page.getPageSize());
                    ps.setInt(3, page.getPageSize());

                }
            }, new PmsItemRowMapper());
            return page.setList(list);
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return page;
        }
    }
    public int pmsCount( String sqlApp){
        StringBuilder builder = new StringBuilder("select count(1) from t_sys_pms_item where 1=1 ");
        try{
            return getJdbcTemplate().queryForInt(builder.append(sqlApp).toString());
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return 0;
        }
    }

    public List<Pms_item> listBySysId(Pms_item pmsItem, final int sysId) {
        List<Pms_item> list= Lists.newArrayList();
        StringBuilder builder = new StringBuilder("SELECT * FROM t_sys_pms_item where 1=1 and sys_id=?  order by pms_item_sequence");
         try{
            list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, sysId);
                }
            }, new PmsItemRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return list;
        }
    }
    public List<Pms_item> listUseAbleBySysIdState(final int  pms_item_state, final int sysId) {
        List<Pms_item> list= Lists.newArrayList();
        StringBuilder builder = new StringBuilder("SELECT * FROM t_sys_pms_item where 1=1 and sys_id=? and pms_item_state=? order by pms_item_sequence");
        try{
            list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, sysId);
                    ps.setInt(2, pms_item_state);
                }
            }, new PmsItemRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return list;
        }
    }
    public Pms_item findById(int pmsItemId) {
        String sql = "SELECT * from   t_sys_pms_item WHERE `id` = ?";
        try{
            Pms_item item = (Pms_item) getJdbcTemplate().queryForObject(sql, new Object[]{pmsItemId}, new int[]{Types.BIGINT}, new PmsItemRowMapper());
            return item;
        } catch (Exception e) {
            LOGGER.error("执行查询Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public boolean update(final Pms_item pmsItem) {
        String sql = "UPDATE `t_sys_pms_item` SET `pms_item_title`=?, `pms_item_name`=?,  `pms_item_source`=?, `pms_item_parent_id`=?, `pms_item_comment`=?, `pms_item_state`=?, `pms_item_sequence`=?, `sys_id`=?, `pms_item_type`=?  WHERE `id`=?";
        try{

            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, pmsItem.getPms_item_title());
                    ps.setString(2, pmsItem.getPms_item_name());
                    ps.setString(3, pmsItem.getPms_item_source());
                    ps.setInt(4, pmsItem.getPms_item_parent_id());
                    ps.setString(5, pmsItem.getPms_item_comment());
                    ps.setByte(6, pmsItem.getPms_item_state());
                    ps.setInt(7, pmsItem.getPms_item_sequence());
                    ps.setInt(8, pmsItem.getSys_id());
                    if(pmsItem.getPms_item_source()!=null&&pmsItem.getPms_item_source().trim()!=""){
                        ps.setInt(9,3);
                    }else if(pmsItem.getPms_item_source()!=null&&pmsItem.getPms_item_source().trim()==""){
                        ps.setInt(9,2);
                    }else{
                        ps.setInt(9,1);
                    }
                    ps.setInt(10, pmsItem.getId());
                }
            });
        } catch (Exception e){
            LOGGER.error("update user exception:{}", e.getMessage(), e);
            return false;
        }
        return true;
    }

    public boolean save(final Pms_item pmsItem) {
        String sql = "INSERT INTO `t_sys_pms_item` ( `pms_item_title`, `pms_item_name`, `pms_item_type`, `pms_item_source`, `pms_item_parent_id`, `pms_item_comment`,`pms_item_state`, `pms_item_sequence`, `sys_id`) " +
                "VALUES (?,?, ?,?, ?,?, ?, ?, ?)";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, pmsItem.getPms_item_title());
                    ps.setString(2,pmsItem.getPms_item_name());
                    if(pmsItem.getPms_item_source()!=null&&pmsItem.getPms_item_source().trim()!=""){
                        ps.setInt(3,3);
                    }else if(pmsItem.getPms_item_source()!=null&&pmsItem.getPms_item_source().trim()==""){
                        ps.setInt(3,2);
                    }else{
                        ps.setInt(3,1);
                    }
                    ps.setString(4,pmsItem.getPms_item_source());
                    ps.setInt(5, pmsItem.getPms_item_parent_id());
                    ps.setString(6,pmsItem.getPms_item_comment());
                    ps.setByte(7,pmsItem.getPms_item_state());
                    ps.setInt(8, pmsItem.getPms_item_sequence());
                    ps.setInt(9,pmsItem.getSys_id());
                }
            });
         return true;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}",e.getMessage(),e);
        }
        return false;
    }

    public boolean updateState(final int pmsItemId,final int state) {
        String sql="UPDATE `t_sys_pms_item` SET pms_item_state=? where id=?";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, state);
                    ps.setInt(2, pmsItemId);
                }
            });
            return true;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}",e.getMessage(),e);
        }
        return false;
    }

    public Map<Integer, Pms_item> mapPmsItem(final int sysId) {
        Map<Integer, Pms_item> pmsItemMap= Maps.newHashMap();
        StringBuilder builder = new StringBuilder("SELECT * FROM t_sys_pms_item where 1=1 and sys_id=? and pms_item_state=0 and pms_item_type!=1 order by pms_item_sequence");
        try{
            List<Pms_item>  list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, sysId);
                }
            }, new PmsItemRowMapper());
            for (int i = 0; i < list.size(); i++) {
                Pms_item item=list.get(i);
                pmsItemMap.put(item.getId(),item);
            }
            return pmsItemMap;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public class PmsItemRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int index) throws SQLException {
            Pms_item item=new Pms_item();
            item.setId(rs.getInt("id"));
            item.setPms_item_comment(rs.getString("pms_item_comment"));
            item.setPms_item_created_date(DateUtils.formatDateTime(rs.getTimestamp("pms_item_created_date")));
            item.setPms_item_name(rs.getString("pms_item_name"));
            item.setPms_item_parent_id(rs.getInt("pms_item_parent_id"));
            item.setPms_item_source(rs.getString("pms_item_source"));
            item.setPms_item_state(rs.getByte("pms_item_state"));
            item.setPms_item_title(rs.getString("pms_item_title"));
            item.setSys_id(rs.getByte("sys_id"));
            item.setPms_item_sequence(rs.getInt("pms_item_sequence"));
            item.setPms_item_parent_id(rs.getInt("pms_item_parent_id"));
            return item;
        }
    }
}
