package com.mokylin.gm.modules.system.web;

import com.google.common.collect.Lists;
import com.mokylin.gm.modules.system.entity.Game;
import com.mokylin.gm.modules.system.entity.Role_config;
import com.mokylin.gm.modules.system.entity.User;
import com.mokylin.gm.modules.system.entity.User_config;
import com.mokylin.gm.modules.system.service.*;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.StringUtils;
import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Controller
@RequestMapping("/gameMgr")
public class GameMgrCtl extends BaseController {
    @Resource
    GameMgrService gameMgrService;
    @Resource
    PmsItemMgrService pmsItemMgrService;
    @Resource
    PmsConfigService pmsConfigService;
    @Resource
    RoleMgrService roleMgrService;
    @Resource
    UserMgrService userMgrService;
    @Resource
    UserService userService;
    @RequiresPermissions("common.menu.gamemanager")
    @RequestMapping(value = {"","treeList"})
    public String treeList(Model model){
        //获取

        List<Game> games=gameMgrService.getGameTreeDatas();
        User user=(User)UserUtils.getCache(UserService.CACHE_USER);
        List<Game> sortedList=Lists.newArrayList();
        if(!user.isAdmin()){
            List<Game> targetList=Lists.newArrayList();
            Game.getPmsGame(games,targetList,user.getPermissions().get("gamearea").getValue());
            Game.sortList(sortedList,targetList,0);
        }else{
            Game.sortList(sortedList,games,0);

        }
        model.addAttribute("gameTreeList",sortedList);
        return "modules/system/gameMgr/gameTreeList";
    }

    @RequestMapping("list")
    @RequiresPermissions("common.menu.gamemanager")
    public String list(Game game,HttpServletRequest request,HttpServletResponse response,Model model){
        //获取
        String parent_id=request.getParameter("parent_id");
        if(StringUtils.isNumeric(parent_id)){
            Page<Game> page=gameMgrService.pageList(new Page<Game>(request,response),Integer.parseInt(parent_id),game);
            model.addAttribute("page",page);
            model.addAttribute("parentGame",gameMgrService.findById(Long.parseLong(parent_id)));
        }
        return "modules/system/gameMgr/gameList";
    }
//    @RequestMapping("edit")
//    public String edit(HttpServletRequest request,Model model){
//        //获取
//        String gameId=request.getParameter("id");
//        if(StringUtils.isNumeric(gameId)){
//            Game game=gameMgrService.findById(Integer.parseInt(gameId));
//            model.addAttribute("game",game);
//        }
//        return "modules/system/gameMgr/gameEdit";
//    }
//    @RequestMapping("add")
//    public String add(HttpServletRequest request,Model model){
//        //获取
//        String gameId=request.getParameter("id");
//        if(StringUtils.isNumeric(gameId)){
//            Game game=gameMgrService.findById(Integer.parseInt(gameId));
//            model.addAttribute("game",game);
//        }
//        return "modules/system/gameMgr/gameAdd";
//    }
    @RequiresPermissions("common.menu.gamemanager.oper")
    @RequestMapping("save")
    @ResponseBody
    public boolean save(HttpServletRequest request,Model model){
        String name=StringUtils.unescape(request.getParameter("name"));
        String level=request.getParameter("level");
        String parent_id=request.getParameter("parent_id");
        String key=request.getParameter("key");
        if(StringUtils.isNumeric(level)&&StringUtils.isNumeric(parent_id)){
            return gameMgrService.save(name,Integer.parseInt(level),Integer.parseInt(parent_id),key);
        }else{
            return false;
        }

    }
    @RequiresPermissions("common.menu.gamemanager.oper")
    @RequestMapping("update")
    @ResponseBody
    public boolean update(HttpServletRequest request,Model model){
        String name=StringUtils.unescape(request.getParameter("name"));
        String status=request.getParameter("status");
        if(status==null){
            status="0";
        }
        String game_id=request.getParameter("game_id");
        String key=request.getParameter("key");
        if(StringUtils.isNumeric(status)&&StringUtils.isNumeric(game_id)){
            return gameMgrService.update(Integer.parseInt(status),Integer.parseInt(game_id),name,key);
        }else{
            return false;
        }

    }
}
