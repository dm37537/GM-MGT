package com.mokylin.gm.modules.system.service;

import java.util.List;

import com.mokylin.gm.modules.system.dao.DictDao;
import com.mokylin.gm.modules.system.entity.Dict;
import com.mokylin.gm.modules.system.utils.DictUtils;
import com.mokylin.gm.modules.system.utils.UserUtils;
import com.mokylin.gm.service.BaseService;
import com.mokylin.gm.utils.CacheUtils;
import com.mokylin.gm.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字典Service
 */
@Service
public class DictService extends BaseService {

	@Autowired
	private DictDao dictDao;
	
	public Page<Dict> find(Page<Dict> page, Dict dict) {
		return dictDao.findPage(page, dict);
	}
	
	public List<String> findTypeList(){
		return dictDao.findTypeList();
	}

//    @Transactional(readOnly = false)
//    public void add(Dict dict) {
//        dictDao.add(dict);
//        CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
//    }

    @Transactional(readOnly = false)
    public boolean update(Dict dict) {
        boolean ret = dictDao.update(dict, UserUtils.getUser().getUserName());
        if(ret)
            CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
        return ret;
    }

    @Transactional(readOnly = false)
	public boolean delete(int id) {
		boolean ret = dictDao.deleteById(id, UserUtils.getUser().getUserName());
        if(ret)
		    CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
        return ret;
	}

    public Dict getDict(int id) {
        return dictDao.get(id);
    }
}
