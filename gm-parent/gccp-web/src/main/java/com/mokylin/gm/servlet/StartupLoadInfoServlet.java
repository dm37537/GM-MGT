package com.mokylin.gm.servlet;

import com.mokylin.gm.component.GameZoneManager;
import com.mokylin.gm.utils.HttpClientUtils;
import com.mokylin.gm.utils.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2014/7/11.
 */
public class StartupLoadInfoServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupLoadInfoServlet.class);

    private static Timer updateGameZoneTimer;

    @Override
    public void init() throws ServletException {
        super.init();
        ResourceManager.refreshResource();
        LOGGER.info("加载国际化信息finish");

        // 初始化游戏区
        GameZoneManager.init();

        // 启动定时任务
        updateGameZoneTimer = new Timer();
        // 周期五分钟
        updateGameZoneTimer.schedule(new UpdateGameZoneTask(), 1000, 300000);
    }

    @Override
    public void destroy() {
        super.destroy();
        LOGGER.error("stop...");
        if(updateGameZoneTimer != null)
            updateGameZoneTimer.cancel();

        // 释放HttpClient cm
        HttpClientUtils.release();

    }

    /**
     * 更新游戏区定时任务
     */
    public class UpdateGameZoneTask extends TimerTask {

        @Override
        public void run() {
            GameZoneManager.updateGameZone();
        }
    }
}
