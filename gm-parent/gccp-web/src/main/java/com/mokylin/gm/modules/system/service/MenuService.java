package com.mokylin.gm.modules.system.service;

import com.mokylin.gm.modules.system.dao.MenuDao;
import com.mokylin.gm.modules.system.entity.Menu;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.service.base.BaseGmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yongbo.chen
 * Time:2014/6/14 18:14
 */
@Service
public class MenuService extends BaseGmService {

    private static final String CACHE_MENU_LIST = "menuList";

    @Resource
    private MenuDao menuDao;

    public List<Menu> getMenuList(User user) {
        List<Menu> menuList = (List<Menu>) UserUtils.getCache(CACHE_MENU_LIST);
        if (menuList == null || menuList.size() == 0) {
            menuList = menuDao.findListByUser();
            UserUtils.putCache(CACHE_MENU_LIST, menuList);
        }
        return menuList;
    }

    /**
     * 查询所有菜单
     * @return
     */
    public List<Menu> findAllMenu() {
        return menuDao.findAllList();
    }

    public boolean updateMenuSort(List<Menu> menus) {
        return menuDao.updateMenuSort(menus);
    }

    /**
     * 根据id获取Menu
     * @param id
     * @return
     */
    public Menu getMenu(int id) {
        return menuDao.getMenu(id);
    }

    public boolean saveMenu(Menu menu) {
        return menuDao.saveMenu(menu, UserUtils.getUser().getId());
    }
}
