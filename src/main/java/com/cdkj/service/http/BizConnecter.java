/**
 * @Title BizConnecter.java 
 * @Package com.ibis.pz.http 
 * @Description 
 * @author miyb  
 * @date 2015-5-12 下午9:44:59 
 * @version V1.0   
 */
package com.cdkj.service.http;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.cdkj.service.LogOutServlet;
import com.cdkj.service.exception.BizException;
import com.cdkj.service.proxy.DispatcherImpl;
import com.cdkj.service.util.DateUtil;
import com.cdkj.service.util.PropertiesUtil;
import com.cdkj.service.util.RegexUtils;

/** 
 * @author: miyb 
 * @since: 2015-5-12 下午9:44:59 
 * @history:
 */
public class BizConnecter {
	static Logger logger = Logger.getLogger(BizConnecter.class);
	
    public static final String YES = "0";

    public static final String USER_URL = PropertiesUtil.Config.USER_URL;

    public static final String ACCOUNT_URL = PropertiesUtil.Config.ACCOUNT_URL;

    public static final String SMS_URL = PropertiesUtil.Config.SMS_URL;

    public static final String FORUM_URL = PropertiesUtil.Config.FORUM_URL;

    public static final String GAME_URL = PropertiesUtil.Config.GAME_URL;

    public static final String ZHPAY_URL = PropertiesUtil.Config.ZHPAY_URL;

    public static final String MALL_URL = PropertiesUtil.Config.MALL_URL;

    public static final String RIDE_URL = PropertiesUtil.Config.RIDE_URL;

    public static final String LOAN_URL = PropertiesUtil.Config.LOAN_URL;

    public static final String TOUR_URL = PropertiesUtil.Config.TOUR_URL;

    public static final String PIPE_URL = PropertiesUtil.Config.PIPE_URL;

    public static final String DZT_URL = PropertiesUtil.Config.DZT_URL;

    public static final String ACTIVITY_URL = PropertiesUtil.Config.ACTIVITY_URL;

    public static String getBizData(String code, String json) {
        String data = null;
        String resJson = null;
        try {
            Properties formProperties = new Properties();
            formProperties.put("code", code);
            formProperties.put("json", json);
            resJson = PostSimulater.requestPostForm(getPostUrl(code),
                formProperties);
        } catch (Exception e) {
            throw new BizException("Biz000", "链接请求超时，请联系管理员");
        }
        // 开始解析响应json
        String errorCode = RegexUtils.find(resJson, "errorCode\":\"(.+?)\"", 1);
        String errorInfo = RegexUtils.find(resJson, "errorInfo\":\"(.+?)\"", 1);
//        System.out.println(DateUtil.dateToStr(new Date(), DateUtil.DATA_TIME_PATTERN_1)+ "  URL:"+ getPostUrl(code) +"\nrequest:" + code + " with parameters " + json
//                + "\nresponse:" + errorCode + "<" + errorInfo + ">.");
        logger.info("URL:"+ getPostUrl(code) +"\nrequest:" + code + " with parameters " + json
                + "\nresponse:" + errorCode + "<" + errorInfo + ">.");
        if (YES.equalsIgnoreCase(errorCode)) {
            data = RegexUtils.find(resJson, "data\":(.*)\\}", 1);
        } else {
            throw new BizException("Biz000", errorInfo);
        }
        return data;
    }

    public static String getPostUrl(String code) {
        String postUrl = null;
        if (code.startsWith("805") || code.startsWith("806")
                || code.startsWith("807") || code.startsWith("001")) {
            postUrl = USER_URL;
        } else if (code.startsWith("802") || code.startsWith("002")) {
            postUrl = ACCOUNT_URL;
        } else if (code.startsWith("803")) {
            postUrl = GAME_URL;
        } else if (code.startsWith("804")) {
            postUrl = SMS_URL;
        } else if (code.startsWith("808")) {
            postUrl = MALL_URL;
        } else if (code.startsWith("610")) {
            postUrl = FORUM_URL;
        } else if (code.startsWith("615")) {
            postUrl = ZHPAY_URL;
        } else if (code.startsWith("616")) {
            postUrl = RIDE_URL;
        } else if (code.startsWith("617")) {
            postUrl = LOAN_URL;
        } else if (code.startsWith("618")) {
            postUrl = TOUR_URL;
        } else if (code.startsWith("619")) {
            postUrl = PIPE_URL;
        } else if (code.startsWith("620")) {
            postUrl = DZT_URL;
        } else if (code.startsWith("660")) {
            postUrl = ACTIVITY_URL;
        }
        return postUrl;
    }
}
