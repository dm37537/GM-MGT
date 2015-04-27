package com.mokylin.gm.modules.system.web;

import com.google.common.collect.Lists;
import com.mokylin.gm.config.Global;
import com.mokylin.gm.modules.system.entity.Game;
import com.mokylin.gm.modules.system.entity.Game_param;
import com.mokylin.gm.modules.system.service.GameMgrService;
import com.mokylin.gm.modules.system.service.GameParamMgrService;
import com.mokylin.gm.utils.Page;
import com.mokylin.gm.utils.StringUtils;
import com.mokylin.gm.web.BaseController;
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
@RequestMapping("/gameMgr/gameParamMgr")
public class GameParamMgrCtl extends BaseController {
    @Resource
    GameParamMgrService gameParamMgrService;
    @Resource
    GameMgrService gameMgrService;
    @RequestMapping("list")
    public String paramList(HttpServletRequest request,Model model){
        String gameId=request.getParameter("gameId");
        if(StringUtils.isNumeric(gameId)){
            Game game=gameMgrService.findById(Long.parseLong(gameId));
           List<Game_param> params= gameParamMgrService.paramList(game.getId());
           List<Game_param> parentParams=gameParamMgrService.paramList(game.getParent_id());//如果没有的参数 代理商的参数要加进去
           List<Game_param> moreParam=Lists.newArrayList();
           for(Game_param pparam:parentParams){
               boolean isHave=false;
               for(Game_param param:params){
                   if(param.getName().equals(pparam.getName())){
                       isHave=true;
                       break;
                   }
               }
               if(!isHave){
                   pparam.setType((byte) 1);
                   moreParam.add(pparam);
               }
           }
            params.addAll(moreParam);
            model.addAttribute("params",params);
            model.addAttribute("game",gameMgrService.findById(Long.parseLong(gameId)));
        }
        return "modules/system/gameMgr/gameParam/paramList";
    }
    @RequestMapping("add")
    public String add(Game game){
        Game game2=gameMgrService.findById(game.getId());
        game2.setGame_params(game.getGame_params());
        boolean isDo=gameParamMgrService.addParams(game2);
        return "redirect:" + Global.getAdminPath() + "/gameMgr/gameParamMgr/list?gameId="+game.getId();
    }
}
