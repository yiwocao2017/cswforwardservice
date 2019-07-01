package com.cdkj.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cdkj.service.enums.EErrorCode;
import com.cdkj.service.http.JsonUtils;
import com.cdkj.service.proxy.ReturnMessage;
import com.cdkj.service.spring.SpringContextHolder;
import com.cdkj.service.token.BooleanRes;
import com.cdkj.service.token.ITokenDAO;
import com.cdkj.service.token.impl.TokenDAOImpl;

/**
 * 退出登录状态
 * @author: xieyj 
 * @since: 2016年12月14日 下午12:55:40 
 * @history:
 */
public class LogOutServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(LogOutServlet.class);

    private ITokenDAO tokenDAO = SpringContextHolder
        .getBean(TokenDAOImpl.class);

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 6175432226630152841L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        logout(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        logout(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tokenId = request.getParameter("token");
        tokenDAO.delToken(tokenId);
        ReturnMessage rm = new ReturnMessage();
        rm.setErrorCode(EErrorCode.SUCCESS.getCode());
        rm.setErrorInfo(EErrorCode.SUCCESS.getValue());
        rm.setData(new BooleanRes(true));
        PrintWriter writer = response.getWriter();
        writer.append(JsonUtils.object2Json(rm));
        writer.flush();
    }

}
