package com.mokylin.gm.modules.system.service;

import com.mokylin.gm.modules.system.dao.LogDao;
import com.mokylin.gm.modules.system.entity.log.Log;
import com.mokylin.gm.modules.system.entity.log.LogCondition;
import com.mokylin.gm.service.base.BaseGmService;
import com.mokylin.gm.utils.DateUtils;
import com.mokylin.gm.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2014/6/30.
 */
@Service
public class LogService extends BaseGmService{

    @Resource
    private LogDao logDao;


    public Page<Log> query(LogCondition logCondition, Page<Log> logPage) {
        Date endDate = DateUtils.parseDate(logCondition.getEndTime());
        if(endDate == null) {
            endDate = new Date();
            logCondition.setEndTime(DateUtils.formatDate(endDate, "yyyy-MM-dd"));
        }

        Date beginDate = DateUtils.parseDate(logCondition.getBeginTime());
        if(beginDate == null) {
            beginDate = DateUtils.addDays(endDate, -7);
            logCondition.setBeginTime(DateUtils.formatDate(beginDate, "yyyy-MM-dd"));
        }

        return logDao.query(logCondition, logPage);
    }
}
