package com.mokylin.gm.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * httpclient工具类
 * Created by Administrator on 2014/7/14.
 */
public class HttpClientUtils {
    private static PoolingHttpClientConnectionManager cm = null;
    private static CloseableHttpClient httpClient = null;
    private static RequestConfig requestConfig;

    static {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(3);
        cm.setDefaultMaxPerRoute(1);

        requestConfig = RequestConfig.custom().setConnectionRequestTimeout(30000).setConnectTimeout(30000).build();

        httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 获取httpclient实例
     * @return
     */
    public static CloseableHttpClient getHttpClient() {
        if(httpClient == null)
            httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
        return httpClient;
    }


    /**
     * 管理连接管理器
     */
    public static void release() {
        if (cm != null) {
            cm.shutdown();
        }
    }
}
