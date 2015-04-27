package com.mokylin.gm.modules.system.service;

import com.mokylin.gm.modules.system.dao.GameDao;
import com.mokylin.gm.modules.system.dao.GameParamDao;
import com.mokylin.gm.modules.system.entity.Game;
import com.mokylin.gm.modules.system.entity.Game_param;
import com.mokylin.gm.service.BaseService;
import com.mokylin.gm.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Service
public class GameParamMgrService extends BaseService {
    @Resource
    GameParamDao gameParamDao;
    public List<Game_param> paramList(long gameId) {
        return gameParamDao.paramList(gameId);
    }
    public boolean addParams(Game game) {
        return gameParamDao.addParam(game);
    }
    public List<Game_param> findAllParam(){
        return gameParamDao.findAllParam();
    }
    public List<Game_param> findAllParam(long super_id){
        return gameParamDao.findAllParam(super_id);
    }
}
