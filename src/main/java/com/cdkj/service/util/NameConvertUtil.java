/**
 * @Title NameFormatConverter.java 
 * @Package com.xnjr.pop.common 
 * @Description 
 * @author xieyj  
 * @date 2015年12月17日 上午9:48:21 
 * @version V1.0   
 */
package com.cdkj.service.util;

/** 
 * 字段命名规则转换(驼峰和下划线互转)
 * @author: xieyj 
 * @since: 2015年12月17日 上午9:48:21 
 * @history:
 */
public class NameConvertUtil {
    public static final char UNDERLINE = '_';

    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
