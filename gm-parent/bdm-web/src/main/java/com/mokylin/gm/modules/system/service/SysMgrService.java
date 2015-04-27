package com.mokylin.gm.modules.system.service;

import com.mokylin.gm.modules.system.dao.SysDao;
import com.mokylin.gm.modules.system.entity.Sys;
import com.mokylin.gm.service.BaseGmService;
import com.mokylin.gm.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Service
public class SysMgrService extends BaseGmService {
    @Resource
    SysDao sysDao;
    public Page<Sys> pageList(Page<Sys> page, Sys sys) {
        return sysDao.pageList(page,sys);
    }
    public Sys findSysById(int id){
        return sysDao.findSysById(id);
    };

    public boolean updateSysById(Sys sys) {
        return sysDao.updateSysById(sys);
    }

    public boolean deleteSysById(int id) {
        return sysDao.deleteSysById(id);
    }

    public boolean save(Sys sys) {
        return sysDao.save(sys);
    }

    public List<Sys> listAll() {
        return sysDao.listAll();
    }
}
