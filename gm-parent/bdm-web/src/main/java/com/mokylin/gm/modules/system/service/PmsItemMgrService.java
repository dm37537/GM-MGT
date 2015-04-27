package com.mokylin.gm.modules.system.service;

import com.mokylin.gm.modules.system.dao.PmsItemDao;
import com.mokylin.gm.modules.system.entity.Pms_item;
import com.mokylin.gm.service.BaseService;
import com.mokylin.gm.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zengweigang on 2014/6/27.
 */
@Service
public class PmsItemMgrService extends BaseService {
    @Resource
    PmsItemDao pmsItemDao;
    public Page<Pms_item> pageListBySysId(final Page<Pms_item> page, Pms_item pmsItem,int sysId){
        return pmsItemDao.pageListBySysId(page, pmsItem, sysId);
    }
    public List<Pms_item> listBySysId(Pms_item pmsItem,int sysId){
        return pmsItemDao.listBySysId(pmsItem, sysId);
    }
    public Pms_item findById(int pmsItemId){
        return pmsItemDao.findById(pmsItemId);
    }

    public boolean update(Pms_item pmsItem) {
        return pmsItemDao.update(pmsItem);
    }

    public boolean save(Pms_item pmsItem) {
        return pmsItemDao.save(pmsItem);
    }

    public boolean updateState(int pmsItemId,int state) {
        return pmsItemDao.updateState(pmsItemId,state);
    }

    public List<Pms_item> listUseAbleBySysIdState(int  pms_item_state,int sysId) {
        return pmsItemDao.listUseAbleBySysIdState(pms_item_state, sysId);
    }

    public Map<Integer, Pms_item> mapPmsItem(int sysId) {
        return pmsItemDao.mapPmsItem(sysId);
    }
}
