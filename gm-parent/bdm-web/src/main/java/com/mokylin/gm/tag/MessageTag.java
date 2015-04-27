package com.mokylin.gm.tag;

import com.mokylin.gm.modules.system.entity.ResourceMessage;
import com.mokylin.gm.utils.CookieUtils;
import com.mokylin.gm.utils.ResourceManager;
import com.mokylin.gm.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 国际化信息 Tag
 * Created by Administrator on 2014/6/20.
 */
public class MessageTag extends TagSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageTag.class);

    private String bundle = null;
    private String key = null;
    private String locale = null;

    public MessageTag() {
    }

    @Override
    public int doEndTag() throws JspException {
        HttpSession session = pageContext.getSession();
        String language = null;
        if(StringUtils.isNotEmpty(locale)){
            language = locale;
        }
        else if(StringUtils.isNotEmpty(CookieUtils.getCookie((HttpServletRequest)pageContext.getRequest(),"language"))) {
            language = CookieUtils.getCookie((HttpServletRequest)pageContext.getRequest(),"language");
            // 将cookie语言设置到session
            session.setAttribute(ResourceManager.RESOURCE_LANGUAGE, language);
        }
        else {
            language = (String) session.getAttribute(ResourceManager.RESOURCE_LANGUAGE);
        }

        try {
            pageContext.getOut().print(ResourceManager.getString(language, bundle, key));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Exception:{}",e.getMessage(),e);
        } catch (IOException e) {
            LOGGER.error("Exception:{}",e.getMessage(),e);
        }
        return super.doEndTag();
    }

    @Override
    public void release() {
        super.release();
    }

    public String getBundle() {
        return bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
