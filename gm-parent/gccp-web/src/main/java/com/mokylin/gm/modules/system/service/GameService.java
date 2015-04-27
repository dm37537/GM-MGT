package com.mokylin.gm.modules.system.service;

import com.mokylin.gm.modules.system.dao.GameDao;
import com.mokylin.gm.modules.system.entity.Game;
import com.mokylin.gm.service.base.BaseGmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2014/7/1.
 */
@Service
public class GameService extends BaseGmService{
    @Resource
    private GameDao gameDao;

    public List<Game> findAllGame(){
       return gameDao.findAllGame();
    }
}
