package com.mokylin.gm.modules.system.dao.action;

import com.google.common.collect.Lists;
import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.Dict;
import com.mokylin.gm.modules.system.entity.Menu;
import com.mokylin.gm.modules.system.entity.action.Action;
import com.mokylin.gm.modules.system.entity.action.ActionCondition;
import com.mokylin.gm.utils.DateUtils;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2014/7/1.
 */
@Repository
public class ActionDao extends BaseGMDao {

    /**
     * 根据条件查找单个action
     * @param action
     * @return
     */
    public Action findAction(final Action action) {
        String sql = "SELECT t_sys_action.id, t_sys_action.menu_id, t_sys_action.game_id, t_sys_action.game_name, t_sys_action.game_version, t_sys_action.has_site, t_sys_action.`name`, t_sys_action.key_name, t_sys_action.url, t_sys_action.adapterClass, t_sys_action.script, t_sys_action.create_by, t_sys_action.create_date,"
                                + " t_sys_menu.id menu_id, t_sys_menu.parent_id, t_sys_menu.parent_ids, t_sys_menu.`name` menu_name, t_sys_menu.href, t_sys_menu.target, t_sys_menu.icon, t_sys_menu.sort, t_sys_menu.is_show, t_sys_menu.permission, t_sys_menu.description, t_sys_menu.game_version, t_sys_menu.del_flag"
                                + " FROM t_sys_action LEFT JOIN t_sys_menu ON t_sys_action.menu_id = t_sys_menu.id where t_sys_action.id= ? ";
        try {
            return getJdbcTemplate().queryForObject(sql, new Object[]{action.getId()}, new int[]{Types.INTEGER}, new RowMapper<Action>() {
                @Override
                public Action mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Action action1 = readAction(rs);
                    action1.setMenu(readMenu(rs));
                    return action1;
                }
            });
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return action;
        }
    }

    /**
     * 查找所有Action
     * @return
     */
    public List<Action> findAllList(ActionCondition condition){
        StringBuilder sql = new StringBuilder("SELECT t_sys_action.id, t_sys_action.menu_id, t_sys_action.game_id, t_sys_action.game_name, t_sys_action.game_version, t_sys_action.has_site, t_sys_action.`name`, t_sys_action.key_name, t_sys_action.url, t_sys_action.adapterClass, t_sys_action.script, t_sys_action.create_by, t_sys_action.create_date,"
                +" t_sys_menu.id menu_id, t_sys_menu.parent_id, t_sys_menu.parent_ids, t_sys_menu.`name` menu_name, t_sys_menu.href, t_sys_menu.target, t_sys_menu.icon, t_sys_menu.sort, t_sys_menu.is_show, t_sys_menu.permission, t_sys_menu.description, t_sys_menu.game_version, t_sys_menu.del_flag"
                +" FROM t_sys_action LEFT JOIN t_sys_menu ON t_sys_action.menu_id = t_sys_menu.id  where 1=1");
        if(condition.getGameId() != 0)
            sql.append(" and t_sys_action.game_id=" + condition.getGameId());
        try {
            return getJdbcTemplate().query(sql.toString(), new RowMapper<Action>() {
                @Override
                public Action mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Action action = readAction(rs);
                    action.setMenu(readMenu(rs));
                    return action;
                }
            });
        }catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return Lists.newArrayList();
        }
    }

    /**
     * 添加Action
     * @param action
     * @return
     */
    public int addAction(final Action action, final String userName) {
        final String sql = "insert into t_sys_action(menu_id, game_id, game_name, game_version, has_site, `name`, key_name, url, adapterClass, script, create_by, create_date) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            getJdbcTemplate().update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    ps.setInt(1, action.getMenu().getId());
                    ps.setInt(2, action.getGameId());
                    ps.setString(3, action.getGameName());
                    ps.setString(4, action.getGameVersion());
                    ps.setString(5, action.getHasSite());
                    ps.setString(6, action.getName());
                    ps.setString(7, action.getKeyName());
                    ps.setString(8, action.getUrl());
                    ps.setString(9, action.getAdapterClass());
                    ps.setString(10, action.getScript());
                    ps.setString(11, userName);
                    ps.setTimestamp(12, new Timestamp(new Date().getTime()));
                    return ps;
                }
            }, keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 修改Action
     * @param action
     * @return
     */
    public boolean updateAction(final Action action) {
        String sql = "update t_sys_action set menu_id=?, game_id=?, game_name=?, game_version=?, has_site=?, `name`=?, key_name=?, url=?, adapterClass=?, script=? where id=? ";
        try {
            getJdbcTemplate().update(sql,new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, action.getMenu().getId());
                    ps.setInt(2, action.getGameId());
                    ps.setString(3, action.getGameName());
                    ps.setString(4, action.getGameVersion());
                    ps.setString(5, action.getHasSite());
                    ps.setString(6, action.getName());
                    ps.setString(7, action.getKeyName());
                    ps.setString(8, action.getUrl());
                    ps.setString(9, action.getAdapterClass());
                    ps.setString(10, action.getScript());
                    ps.setInt(11, action.getId());
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 删除Action
     * @param actionId
     * @return
     */
    public boolean deleteAction(final Integer actionId) {
        String sql = "delete from t_sys_action where id = ? ";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, actionId);
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 读取Menu信息
     * @param rs
     * @return
     */
    private Menu readMenu(ResultSet rs) {
        Menu menu = new Menu();
        try {
            menu.setId(rs.getInt("menu_id"));
            menu.setParentId(rs.getInt("parent_id"));
            menu.setName(rs.getString("menu_name"));
            menu.setHref(rs.getString("href"));
            menu.setTarget(rs.getString("target"));
            menu.setIcon(rs.getString("icon"));
            menu.setShow(rs.getInt("is_show") == Dict.IS_SHOW);
            menu.setPermission(rs.getString("permission"));
            menu.setDescription(rs.getString("description"));
//            menu.setGameVersion(rs.getString("game_version"));
        }catch (Exception e){
            LOGGER.error("Read Result Exception:{}", e.getMessage(), e);
        }
        return menu;
    }

    /**
     * 读取Action信息
     * @param rs
     * @return
     */
    private Action readAction(ResultSet rs) {
        Action action = new Action();
        try {
            action.setId(rs.getInt("id"));
            action.setGameId(rs.getInt("game_id"));
            action.setGameName(rs.getString("game_name"));
            action.setGameVersion(rs.getString("game_version"));
            action.setHasSite(rs.getString("has_site"));
            action.setName(rs.getString("name"));
            action.setKeyName(rs.getString("key_name"));
            action.setUrl(rs.getString("url"));
            action.setAdapterClass(rs.getString("adapterClass"));
            action.setScript(rs.getString("script"));
            action.setCreateBy(rs.getString("create_by"));
            action.setCreateDate(DateUtils.formatDateTime(rs.getTimestamp("create_date").getTime()));
        }catch (Exception e){
            LOGGER.error("Read Result Exception:{}", e.getMessage(), e);
        }
        return action;
    }
}
