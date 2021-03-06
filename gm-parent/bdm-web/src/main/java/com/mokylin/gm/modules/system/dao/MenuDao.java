package com.mokylin.gm.modules.system.dao;

import com.google.common.collect.Lists;
import com.mokylin.gm.dao.BaseGMDao;
import com.mokylin.gm.modules.system.entity.Dict;
import com.mokylin.gm.modules.system.entity.Menu;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.service.UserService;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.*;
import java.util.*;

/**
 * Created by yongbo.chen
 * Time:2014/6/14 18:25
 */
@Repository
public class MenuDao extends BaseGMDao {

    public List<Menu> findAllList() {
        String sql = "SELECT id, parent_id, parent_ids, `name`, href, target, icon, sort, is_show, permission, description FROM t_sys_menu WHERE del_flag = " + Dict.DEL_FLAG_NORMAL + " ORDER BY sort";
        List<Menu> menuList = getJdbcTemplate().query(sql, new MenuRowMapper());

        // 组装Menu
        Menu.assembledMenu(menuList);
        return menuList;
    }

    /**
     * 根据用户Id 获取菜单列表
     *
     * @param id
     * @return
     */
    public List<Menu> findByUserId(long id) {
//        String sql = "SELECT DISTINCT menu.id, menu.create_by, menu.create_date, menu.del_flag, menu.description, menu.update_by, menu.update_date, menu.href, menu.icon, menu.is_show, menu.`name`, menu.parent_id, menu.parent_ids, menu.permission, menu.sort, menu.target FROM" +
//                " t_sys_menu menu CROSS JOIN t_sys_role role CROSS JOIN t_sys_user user WHERE (menu.id IN (SELECT menu_id FROM t_sys_role_menu role_menu WHERE role.id = role_menu.role_id))" +
//                " AND ( role.id IN ( SELECT user_role.role_id FROM t_sys_user_role user_role WHERE user.id = user_role.user_id ))" +
//                " AND menu.del_flag =0 AND role.del_flag =0 AND user.status =1 AND user.id = ? ORDER BY menu.sort";
//        List<Menu> menuList = getJdbcTemplate().query(sql, new Object[]{id}, new int[]{Types.BIGINT}, new MenuRowMapper());
        Subject subject=SecurityUtils.getSubject();
        List<Menu> menuList= Lists.newArrayList();
        List<Menu> menuListAll = findAllList();
        for(Menu menu:menuListAll){
            if(menu.getPermission()==null||"".equals(menu.getPermission().trim())){
                menuList.add(menu);
                continue;
            }
            if(menu.getPermission()!=null&&subject.isPermitted(menu.getPermission().trim())){
                menuList.add(menu);
                continue;
            }
        }
        // 组装Menu
        Menu.assembledMenu(menuList);
        return menuList;
    }
    /**
     * 更新菜单排序
     */
    public boolean updateMenuSort(final List<Menu> menus){
        String sql = "UPDATE `t_sys_menu` set `sort`=? where id= ?";
        try{
            getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, menus.get(i).getSort());
                    ps.setInt(2, menus.get(i).getId());
                }

                @Override
                public int getBatchSize() {
                    return menus.size();
                }
            });
            return true;
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return  false;
        }
    }

    /**
     * 根据id 获取Menu
     * @param id
     * @return
     */
    public Menu getMenu(int id) {
        String sql = "SELECT id, parent_id, parent_ids, `name`, href, target, icon, sort, is_show, permission, description FROM t_sys_menu WHERE id = ? and del_flag = " + Dict.DEL_FLAG_NORMAL + " ORDER BY sort";
        try{
            return (Menu)getJdbcTemplate().queryForObject(sql, new Object[]{id}, new int[]{Types.INTEGER}, new MenuRowMapper());
        } catch (Exception e) {
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return null;
        }
    }

    public boolean saveMenu(final Menu menu, final Long userId) {
        String sql = "UPDATE `t_sys_menu` set `name`=?, href=?, target=?, icon=?, sort=?, is_show=?, permission=?, update_by=?, update_date=? where id=? ";
        try{
            getJdbcTemplate().update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, menu.getName());
                    ps.setString(2, menu.getHref());
                    ps.setString(3, menu.getTarget());
                    ps.setString(4, menu.getIcon());
                    ps.setInt(5, menu.getSort());
                    ps.setInt(6, menu.isShow() ? Dict.IS_SHOW : Dict.NO_SHOW);
                    ps.setString(7, menu.getPermission());
                    ps.setLong(8, userId);
                    ps.setTimestamp(9, new Timestamp(new java.util.Date().getTime()));
                    ps.setInt(10, menu.getId());
                }
            });
        }catch (Exception e){
            LOGGER.error("DB Exception:{}", e.getMessage(), e);
            return false;
        }
        return true;
    }

    public class MenuRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int index) throws SQLException {
            Menu menu = new Menu(rs.getInt("id"));
            menu.setParentId(rs.getInt("parent_id"));
            menu.setParentIds(rs.getString("parent_ids"));
            menu.setName(rs.getString("name"));
            menu.setHref(rs.getString("href"));
            menu.setTarget(rs.getString("target"));
            menu.setIcon(rs.getString("icon"));
            menu.setSort(rs.getInt("sort"));
            menu.setShow(rs.getInt("is_show") == Dict.IS_SHOW);
            menu.setPermission(rs.getString("permission"));
            menu.setDescription(rs.getString("description"));
            return menu;
        }
    }
}

