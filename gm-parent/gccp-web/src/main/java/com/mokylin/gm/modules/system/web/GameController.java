package com.mokylin.gm.modules.system.web;

import com.mokylin.gm.modules.system.service.GameService;
import com.mokylin.gm.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 游戏及版本 Controller
 * Created by Administrator on 2014/7/1.
 */
@Controller
@RequiresUser
@RequestMapping(value = "/system/game")
public class GameController  extends BaseController{

    @Resource
    private GameService gameService;

    @RequiresPermissions(value = "gameversion")
    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        model.addAttribute("list", gameService.findAllGame());
        return "modules/system/game/gameList";
    }
}
