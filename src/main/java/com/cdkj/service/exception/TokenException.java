/**
 * @Title BizException.java 
 * @Package com.ibis.account.exception 
 * @Description 
 * @author miyb  
 * @date 2015-5-16 下午1:30:31 
 * @version V1.0   
 */
package com.cdkj.service.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/** 
 * @author: miyb 
 * @since: 2015-5-16 下午1:30:31 
 * @history:
 */
public class TokenException extends RuntimeException {
    private static final long serialVersionUID = -6853310712844466349L;

    private static final Object[] EMPTY_PARAMS = new Object[0];

    private String errorCode = "-1";

    private String errorMessage = "";

    /** 错误参数清单 */
    private Object[] errorParams;

    /** 按照put的顺序保存错误参数 */
    private List<String> errorPropNames = new ArrayList<String>();

    private Map<String, Object> errorProperties = new HashMap<String, Object>();

    public TokenException(String errorCode) {
        this.errorCode = errorCode;
    }

    public TokenException(String errorCode, Object... errorParams) {
        this.errorCode = errorCode;
        this.errorParams = errorParams;
    }

    public TokenException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public TokenException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public TokenException(Throwable cause, String errorCode, Object[] errorParams) {
        super(cause);
        this.errorCode = errorCode;
        this.errorParams = errorParams;
    }

    public TokenException put(String name, Object prop) {
        if (name != null) {
            errorPropNames.add(name);
            errorProperties.put(name, prop);
        }
        return this;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getErrorParams() {
        Object[] errorParams = EMPTY_PARAMS;
        if (this.errorParams != null) {
            errorParams = this.errorParams;
        }
        if (errorPropNames.size() == 0) {
            return errorParams;
        }
        // 如果存在带key的参数则追加在返回结果中
        ArrayList<Object> params = new ArrayList<Object>(
            Arrays.asList(errorParams));
        for (String name : errorPropNames) {
            params.add(errorProperties.get(name));
        }
        return params.toArray();
    }

    public List<String> getErrorPropNames() {
        return errorPropNames;
    }

    public Map<String, Object> getErrorProperties() {
        return errorProperties;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorParams(Object[] errorParams) {
        this.errorParams = errorParams;
    }

    public void setErrorPropNames(List<String> errorPropNames) {
        this.errorPropNames = errorPropNames;
    }

    public void setErrorProperties(Map<String, Object> errorProperties) {
        this.errorProperties = errorProperties;
    }

    public String getMessage() {
        String message = super.getMessage();
        if (StringUtils.isNotEmpty(errorMessage)) {
            message = errorMessage;
        }
        if (StringUtils.isEmpty(message)) {
            StringBuilder sb = new StringBuilder();
            if (errorParams != null) {
                for (int i = 0; i < errorParams.length; i++) {
                    if (i == 0) {
                        sb.append(errorParams[i]);
                    } else {
                        sb.append(", " + errorParams[i]);
                    }
                }
                if (errorParams.length > 0 && errorPropNames.size() > 0) {
                    sb.append(", ");
                }
            }
            int errorPropSize = errorPropNames.size();
            for (int i = 0; i < errorPropSize; i++) {
                String propName = errorPropNames.get(i);
                Object propValue = errorProperties.get(propName);
                if (i == 0) {
                    sb.append(propName + "=" + propValue);
                } else {
                    sb.append(", " + propName + "=" + propValue);
                }
            }
            sb.append(" [" + errorCode + "]");
            message = sb.toString();
        }
        return message;
    }

}
