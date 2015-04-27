package com.mokylin.gm.modules.system.dao;

import com.google.common.collect.Lists;
import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.Game;
import com.mokylin.gm.modules.system.entity.Pms_item;
import com.mokylin.gm.modules.system.entity.Role;
import com.mokylin.gm.utils.DateUtils;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.StringUtils;
import org.apache.commons.collections.map.PredicatedSortedMap;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Repository
public class GameDao extends BaseGMDao {

    public List<Game> getGameTreeDatas() {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_game where level<=2");
        try{
            List<Game> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                }
            }, new GameRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }
    public List<Game> getAllGameTreeDatas(final long superId) {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_game where  super_id=? ");
        try{
            List<Game> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setLong(1,superId);
                }
            }, new GameRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }
    public List<Game> getAllGameTreeDatas() {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_game  ");
        try{
            List<Game> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                }
            }, new GameRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }
    public List<Game> getAllGameTreeDatas(final long superId,final String date) {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_game where  super_id=? and  update_time>?");
        try{
            List<Game> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setLong(1,superId);
                    ps.setDate(2,new Date((DateUtils.parseDate(date)).getTime()));
                }
            }, new GameRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }
    public List<Game> getAllGameTreeDatas(final String dateStr) {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_game where update_time>? ");
        try{
            List<Game> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setTimestamp(1, new Timestamp(DateUtils.parseDate(dateStr).getTime()));
                }
            }, new GameRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }
    public Game findById(Long id) {
        String sql = "SELECT *  FROM t_game WHERE `id` = ?";
        try{
            Game game = (Game) getJdbcTemplate().queryForObject(sql, new Object[]{id}, new int[]{Types.BIGINT}, new GameRowMapper());
            return game;
        } catch (Exception e) {
            LOGGER.error("执行查询Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public Page<Game> pageList(final Page<Game> page, final int parent_id,Game game) {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_game where 1=1  ");
        StringBuilder builder1 = new StringBuilder();
        builder1.append(" and parent_id="+parent_id);
        if(!page.isDisabled() && !page.isNotCount()){
            page.setCount(gameCount(builder1.toString()));
        }
        if(null != game){
            if(game.getId()!=null&&game.getId()!=0)
                builder1.append(" and id="+game.getId() );
            if(StringUtils.isNotEmpty(game.getName()))
                builder1.append(" and name like '%" + game.getName() + "%'" );
        }
        builder.append(builder1).append(" limit ?, ?");

        try{
            List<Game> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, (page.getPageNo()-1) * page.getPageSize());
                    ps.setInt(2, page.getPageSize());
                }
            }, new GameRowMapper());
            return page.setList(list);
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return page;
        }
    }

    private long gameCount(String sqlApp) {
        StringBuilder builder = new StringBuilder("select count(1) from t_game where 1=1 ");
        try{
            return getJdbcTemplate().queryForInt(builder.append(sqlApp).toString());
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return 0;
        }
    }

    public boolean save(final String name, final int level, final long parent_id,final String key) {
        //获取父类的super_id
        long super_id=0;
        if(level==0){
            super_id=0;
        }else{
            Game parent=findById(parent_id);
            if(parent.getSuper_id()==0){
                super_id=parent.getId();
            }else{
                super_id=parent.getSuper_id();
            }

        }
        final long super_idF=super_id;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql="INSERT INTO `t_game` (`name`, `parent_id`, `level`,`super_id`,`key`,create_time) VALUES (?, ?, ?,?,?,?)";
        try {
            getJdbcTemplate().update(new PreparedStatementCreator() {
                                         @Override
                                         public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                             PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                                             ps.setString(1,name);
                                             ps.setLong(2,parent_id );
                                             ps.setInt(3, level);
                                             ps.setLong(4,super_idF);
                                             ps.setString(5,key==null?"":key);
                                             ps.setDate(6,new Date(System.currentTimeMillis()));
                                             return ps;
                                         }
                                     },keyHolder);
            if(level==0){//如果等级为0  更新super_id 为id
                final int id=keyHolder.getKey().intValue();
                String updatesql="update  `t_game` set `super_id`=?  where id=?";
                getJdbcTemplate().update(updatesql,new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setLong(1, id);
                        ps.setLong(2, id);
                    }
                });
            }
            return true;
        }catch (Exception e){
            LOGGER.error("添加游戏Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    public boolean update(final int status, final int game_id, final String name,final String key) {
        String sql="update  `t_game` set name=?,status=?,`key`=?  where id=?";
        try {
            getJdbcTemplate().update(sql,new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, name);
                    ps.setInt(2,status);
                    ps.setInt(4,game_id);
                    ps.setString(3, key==null?"":key);
                }
            });
            return true;
        }catch (Exception e){
            LOGGER.error("更新游戏Exception:{}", e.getMessage(), e);
            return false;
        }
    }


    public class GameRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int index) throws SQLException {
            Game game=new Game();
            game.setCreate_time(DateUtils.formatDateTime(rs.getTimestamp("create_time")));
            game.setId(rs.getLong("id"));
            game.setLevel(rs.getInt("level"));
            game.setName(rs.getString("name"));
            game.setParent_id(rs.getInt("parent_id"));
            game.setStatus(rs.getByte("status"));
            game.setSuper_id(rs.getLong("super_id"));
            game.setKey(rs.getString("key"));
            game.setUpdate_time(DateUtils.formatDateTime(rs.getTimestamp("update_time")));
            return game;
        }
    }

    public static void main(String[] args) {
        java.util.Date date=DateUtils.parseDate("2014-07-15 19:14:18");

        System.out.print(DateUtils.formatDateTime(new Date(date.getTime())));
    }
}
