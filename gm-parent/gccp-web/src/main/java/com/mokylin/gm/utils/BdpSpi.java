package com.mokylin.gm.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mokylin.gm.config.Global;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by yongbo.chen
 * Time:2014/7/15 20:57
 */
public class BdpSpi {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdpSpi.class);

    private static final String BDP_URL = "bdp.url";
    private static final String API_GameZone = "api.gamezone";
    private static final String API_GameZoneUpdate = "api.gamezoneupdate";
    private static final String API_Login = "api.login";
    private static final String API_PassWord = "api.password";

    private static final String SystemId = "system.id";
    private static final String VerifyCode = "verify.code";

    private String bdpUrl;

    public BdpSpi(){
        bdpUrl = Global.getConfig(BDP_URL);
    }

    /**
     * 获取所有游戏区信息
     * @return
     */
    public String  downLoadAllGameZoneInfo() {
        Map<String, Object> param = Maps.newHashMap();
        param.put("sys_id", Global.getConfig(SystemId));
        param.put("verify_code", Global.getConfig(VerifyCode));

        return postData(API_GameZone, param);
    }

    /**
     * 更新游戏区信息
     */
    public String updateGameZoneInfo(String date) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("sys_id", Global.getConfig(SystemId));
        param.put("verify_code", Global.getConfig(VerifyCode));
        param.put("date", date);

        return postData(API_GameZoneUpdate, param);
    }

    /**
     * 验证登录
     * @param userName
     * @param passWord
     * @return
     */
    public String loginValidate(String userName, String passWord){
        Map<String, Object> param = Maps.newHashMap();
        param.put("user_name", userName);
        param.put("pass_word", passWord);
        param.put("sys_id", Global.getConfig(SystemId));
        param.put("verify_code", Global.getConfig(VerifyCode));

        return postData(API_Login, param);
    }

    /**
     * 修改密码
     * @return
     */
    public String modifyPassword(String userName, String newPass, String oldPass) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("user_name", userName);
        param.put("new_password", newPass);
        param.put("old_password", oldPass);
        param.put("sys_id", Global.getConfig(SystemId));
        param.put("verify_code", Global.getConfig(VerifyCode));
        return postData(API_PassWord, param);
    }

    private String postData(String apiName, Map parameters) {
        HttpContext httpContext = HttpClientContext.create();
        HttpPost httpPost = new HttpPost(bdpUrl + Global.getConfig(apiName));
        String ret = null;

        List<NameValuePair> nvps = Lists.newArrayList();
        for (Iterator<Entry<String, Object>> it = parameters.entrySet().iterator(); it.hasNext();)
        {
            Entry<String, Object> entry = it.next();
            nvps.add(new BasicNameValuePair(entry.getKey(), (entry.getValue().toString())));
        }

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response = HttpClientUtils.getHttpClient().execute(httpPost, httpContext);
            if(response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();

                ret = EntityUtils.toString(entity, "UTF-8");
                try {
                    // 关闭资源
                    EntityUtils.consume(entity);
                } finally {
                    response.close();
                }
            }
            else{
                httpPost.abort();
            }
        } catch (Exception e) {
            LOGGER.error("调用接口异常:{}", e.getMessage(), e);
            return "";
        }
        return ret;
    }
}
