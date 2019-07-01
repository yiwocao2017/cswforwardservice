/**
 * @Title BaseController.java 
 * @Package com.hsnet.pz.controller 
 * @Description 
 * @author miyb  
 * @date 2014-8-19 上午10:54:17 
 * @version V1.0   
 */
package com.cdkj.service.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdkj.service.base.ControllerContext;
import com.cdkj.service.common.JsonUtil;
import com.cdkj.service.dto.req.XN805158Req;
import com.cdkj.service.enums.EErrorCode;
import com.cdkj.service.enums.ETokenPrefix;
import com.cdkj.service.exception.BizException;
import com.cdkj.service.http.BizConnecter;
import com.cdkj.service.http.JsonUtils;
import com.cdkj.service.proxy.ReturnMessage;
import com.cdkj.service.token.BooleanRes;
import com.cdkj.service.token.ITokenDAO;

@Controller
public class BaseController {

    @Autowired
    protected ITokenDAO tokenDAO;

    /**
     * 获取IP地址
     * 
     * @return
     */
    protected String getRemoteHost() {
        String ip = ControllerContext.getRequest().getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = ControllerContext.getRequest().getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = ControllerContext.getRequest().getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = ControllerContext.getRequest().getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    @RequestMapping(value = "/ip", method = RequestMethod.GET)
    public void getIp(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.append("{ \"ip\" : \"" + getRemoteHost() + "\" }");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/user/logOut", method = RequestMethod.POST)
    @ResponseBody
    public void logOut(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("********************登出开始**********************");
        String tokenId = request.getParameter("token");
        if (StringUtils.isBlank(tokenId)) {
            throw new BizException("xn0000", "token必填，请输入");
        }
        tokenDAO.delToken(tokenId);
        String userId = tokenId.substring(
            tokenId.indexOf(ETokenPrefix.TU.getCode()) + 1,
            tokenId.indexOf(ETokenPrefix.TK.getCode()));
        doRemoveLocation(userId);
        // 将用户的经纬度设置成空
        ReturnMessage rm = new ReturnMessage();
        rm.setErrorCode(EErrorCode.SUCCESS.getCode());
        rm.setErrorInfo(EErrorCode.SUCCESS.getValue());
        rm.setData(new BooleanRes(true));
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.append(JsonUtils.object2Json(rm));
            writer.flush();
        } catch (IOException e) {
            throw new BizException("xn0000", "登出失败");
        }
        System.out.println("********************登出结束**********************");
    }

    private void doRemoveLocation(String userId) {
        XN805158Req req = new XN805158Req();
        req.setUserId(userId);
        req.setLatitude("-1");
        req.setLongitude("-1");
        BizConnecter.getBizData("805158", JsonUtil.Object2Json(req));
    }
}
