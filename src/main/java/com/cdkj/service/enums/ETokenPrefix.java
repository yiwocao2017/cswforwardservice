/**
 * @Title ETokenPrefix.java 
 * @Package com.cdkj.service.enums 
 * @Description 
 * @author xieyj  
 * @date 2016年12月18日 下午11:05:23 
 * @version V1.0   
 */
package com.cdkj.service.enums;

/** 
 * @author: xieyj 
 * @since: 2016年12月18日 下午11:05:23 
 * @history:
 */
public enum ETokenPrefix {
    TK("TK", "token前缀"), TU("T", "userId前缀");

    ETokenPrefix(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
