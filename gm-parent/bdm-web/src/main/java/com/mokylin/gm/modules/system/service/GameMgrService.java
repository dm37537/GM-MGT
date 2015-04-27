package com.mokylin.gm.modules.system.service;

import com.mokylin.gm.modules.system.dao.GameDao;
import com.mokylin.gm.modules.system.dao.PmsItemDao;
import com.mokylin.gm.modules.system.entity.Game;
import com.mokylin.gm.modules.system.entity.Pms_item;
import com.mokylin.gm.service.BaseService;
import com.mokylin.gm.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Service
public class GameMgrService extends BaseService {
    @Resource
    GameDao gameDao;
    public List<Game> getGameTreeDatas() {
        return gameDao.getGameTreeDatas();
    }
    public List<Game> getAllGameTreeDatas(Long superId) {
        return gameDao.getAllGameTreeDatas(superId);
    }
    public List<Game> getAllGameTreeDatas() {
        return gameDao.getAllGameTreeDatas();
    }
    public List<Game> getAllGameTreeDatas(Long superId,String date) {
        return gameDao.getAllGameTreeDatas(superId,date);
    }
    public List<Game> getAllGameTreeDatas(String date) {
        return gameDao.getAllGameTreeDatas(date);
    }
    public Game findById(Long id) {
        return gameDao.findById(id);
    }

    public Page<Game> pageList(Page<Game> gamePage, int parent_id,Game game) {
        return gameDao.pageList(gamePage,parent_id,game);
    }


    public boolean save(String name, int level, int parent_id,String key) {
        return gameDao.save(name,level,parent_id,key);
    }

    public boolean update(int status, int game_id, String name,String key) {
        return gameDao.update(status,game_id,name,key);
    }
}
