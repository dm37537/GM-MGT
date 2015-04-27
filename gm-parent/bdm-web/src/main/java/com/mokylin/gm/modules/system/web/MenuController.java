package com.mokylin.gm.modules.system.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mokylin.gm.config.Global;
import com.mokylin.gm.entity.MessageType;
import com.mokylin.gm.entity.ResultMsg;
import com.mokylin.gm.modules.system.entity.Menu;
import com.mokylin.gm.modules.system.service.MenuService;
import com.mokylin.gm.utils.ResourceManager;
import com.mokylin.gm.utils.StringUtils;
import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 菜单Controller
 * Created by Administrator on 2014/6/19.
 */
@Controller
@RequestMapping( value = "/system/menu")
public class MenuController extends BaseController{
    @Resource
    private MenuService menuService;


    /**
     * 菜单列表
     * @return
     */
    @RequiresPermissions("common.menu.menumanage")
    @RequestMapping(value = {"list", ""})
    public String list(Model model){
        List<Menu> list = Lists.newArrayList();
        List<Menu> sourceList = menuService.findAllMenu();
        Menu.sortList(list, sourceList, 1);
        model.addAttribute("list", list);
        return "modules/system/menu/menuList";
    }

    /**
     * 修改菜单跳转
     */
    @RequiresPermissions("common.menu.menumanage.oper")
    @RequestMapping(value = "formThis")
    public String formThis(@ModelAttribute(value = "menu") Menu menu, Model  model){
        if(menu.getId() != 0){
            menu = menuService.getMenu(menu.getId());
            menu.setParent(menuService.getMenu(menu.getParentId()));
        }
        model.addAttribute("menu", menu);
        return "modules/system/menu/menuForm";
    }
    /**
     * 添加子菜单
     */
    @RequiresPermissions("common.menu.menumanage.oper")
    @RequestMapping(value = "formChild")
    public String formChild(@ModelAttribute(value = "menu") Menu menu, Model  model){
        if(menu.getParent() == null || menu.getParent().getId() == 0){
            menu.setParent(new Menu(1));
        }
        menu.setParent(menuService.getMenu(menu.getParent().getId()));

        model.addAttribute("menu", menu);
        return "modules/system/menu/menuForm";
    }

    /**
     * 添加、修改菜单
     */
    @RequiresPermissions("common.menu.menumanage.oper")
    @RequestMapping(value = "save")
    public String save(Menu menu, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        if(menuService.saveMenu(menu))
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString(request, "common", "common.save.success")));
        else
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString(request, "common", "common.save.failed")));
        return "redirect:" + Global.getAdminPath() + "/system/menu/list";
    }


    /**
     * 菜单列表(左侧菜单树)
     * @return
     */
    @RequiresUser
    @RequestMapping( value = "tree")
    public String tree() {
        return "modules/system/menuTree";
    }

    /**
     * 批量修改菜单排序
     */
    @RequiresPermissions("common.menu.menumanage.oper")
    @RequestMapping(value = "updateSort")
    public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
        int len = ids.length;
        List<Menu> menus = Lists.newArrayList();
        for (int i = 0; i < len; i++){
            Menu menu = new Menu();
            menu.setId(StringUtils.toInteger(ids[i]));
            menu.setSort(StringUtils.toInteger(sorts[i]));
            menus.add(menu);
        }

        if(menuService.updateMenuSort(menus))
            addMessage(redirectAttributes, new ResultMsg(1, MessageType.SUCCESS, ResourceManager.getString(request, "common", "common.save.success")));
        else
            addMessage(redirectAttributes, new ResultMsg(0, MessageType.ERROR, ResourceManager.getString(request, "common", "common.save.failed")));
        return "redirect:" + Global.getAdminPath() + "/system/menu/list";

    }

    @RequiresUser
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) Integer extId, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json; charset=UTF-8");
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Menu> list = menuService.findAllMenu();
        for (int i=0; i<list.size(); i++){
            Menu e = list.get(i);
            if (extId == 0 || (extId!=0 && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParent()!=null?e.getParent().getId():0);
                // 替换国际化
                map.put("name", ResourceManager.getString(request, "common",e.getName()));
                mapList.add(map);
            }
        }
        return mapList;
    }
}
