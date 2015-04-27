package com.mokylin.gm.modules.system.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.Game;
import com.mokylin.gm.utils.StringUtils;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * 游戏列表 DAO
 * Created by Administrator on 2014/6/30.
 */
@Repository
public class GameDao extends BaseGMDao{

    private ObjectMapper mapper = new ObjectMapper();

    public List<Game> findAllGame() {
        String sql = "SELECT id, gameId, gameName, gameVersion FROM `t_sys_game`";
        try{
            return getJdbcTemplate().query(sql, new GameRowMapper());
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public Game getGameByName(String name) {
        String sql = "select id, gameId, gameName, gameVersion from t_sys_game where gameName = ? ";
        try {
            return (Game)getJdbcTemplate().queryForObject(sql, new Object[]{name}, new int[]{Types.VARCHAR}, new GameRowMapper());
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public class GameRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Game game = new Game();
            game.setId(rs.getInt("id"));
            game.setGameId(rs.getInt("gameId"));
            game.setGameName(rs.getString("gameName"));
            String versionTmp = rs.getString("gameVersion");

            try {
                if(StringUtils.isNotEmpty(versionTmp))
                    game.setVersions( mapper.readValue(versionTmp, List.class));
            } catch (IOException e) {
                LOGGER.error("Json Exception:{}", e.getMessage(), e);
            }
            return game;
        }
    }
}
