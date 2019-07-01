package com.cdkj.service.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties props;
    static {
        props = new Properties();
        try {
            props.load(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("config.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("找不到config.properties文件", e);
        } catch (IOException e) {
            throw new RuntimeException("读取config.properties文件出错", e);
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    public static final class Config {
        public static String USER_URL = props.getProperty("USER_URL");

        public static String ACCOUNT_URL = props.getProperty("ACCOUNT_URL");

        public static String SMS_URL = props.getProperty("SMS_URL");

        public static String ZHPAY_URL = props.getProperty("ZHPAY_URL");

        public static String RIDE_URL = props.getProperty("RIDE_URL");

        public static String LOAN_URL = props.getProperty("LOAN_URL");

        public static String TOUR_URL = props.getProperty("TOUR_URL");

        public static String GAME_URL = props.getProperty("GAME_URL");

        public static String PIPE_URL = props.getProperty("PIPE_URL");

        public static String MALL_URL = props.getProperty("MALL_URL");

        public static String FORUM_URL = props.getProperty("FORUM_URL");

        public static String DZT_URL = props.getProperty("DZT_URL");

        public static String ACTIVITY_URL = props.getProperty("ACTIVITY_URL");
    }
}
