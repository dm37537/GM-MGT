package com.mokylin.gm.modules.system.dao;

import com.google.common.collect.Lists;
import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.Game;
import com.mokylin.gm.modules.system.entity.Game_param;
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
import java.util.Arrays;
import java.util.List;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Repository
public class GameParamDao extends BaseGMDao {


    public List<Game_param> paramList(final long gameId) {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_game_param where 1=1 ");
        builder.append(" and game_id = ?" );
        try{
            List<Game_param> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setLong(1,gameId);
                }
            }, new GameParamRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }
    public boolean addParam(final Game game) {

        String sql="INSERT INTO `t_game_param` (`name`, `val`, `game_id`, `comment`, `type`, `super_id`) VALUES (?,?,?,?,?,?)";
        final List<Game_param> params= game.getGame_params();
        try {
            //删除所有值为 1的 开关权限
            getJdbcTemplate().update("delete from `t_game_param` where game_id=?",new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setLong(1,game.getId());
                }
            });
            getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Game_param param=params.get(i);
                    ps.setString(1,param.getName());
                    ps.setString(2,param.getVal());
                    ps.setLong(3,game.getId());
                    ps.setString(4,param.getComment());
                    ps.setByte(5,param.getType());
                    ps.setLong(6,game.getSuper_id());
                }

                @Override
                public int getBatchSize() {
                    return params.size();
                }
            });
            return true;
        }catch (Exception e){
            LOGGER.error("添加 游戏参数Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    public List<Game_param> findAllParam() {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_game_param where 1=1 ");
        try{
            List<Game_param> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                }
            }, new GameParamRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }
    public List<Game_param> findAllParam(final long super_id) {
        StringBuilder builder = new StringBuilder("SELECT * FROM t_game_param where 1=1 and super_id=?");
        try{
            List<Game_param> list = getJdbcTemplate().query(builder.toString(), new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setLong(1,super_id);
                }
            }, new GameParamRowMapper());
            return list;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }
    public class GameParamRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int index) throws SQLException {
            Game_param param=new Game_param();
            param.setName(rs.getString("name"));
            param.setComment(rs.getString("comment"));
            param.setCreate_time(DateUtils.formatDateTime(rs.getTimestamp("create_time")));
            param.setGame_id(rs.getInt("game_id"));
            param.setId(rs.getInt("id"));
            param.setType(rs.getByte("type"));
            param.setVal(rs.getString("val"));
            param.setSuper_id(rs.getLong("super_id"));
            return param;
        }
    }
}
