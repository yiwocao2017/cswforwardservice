package com.cdkj.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cdkj.service.session.ISessionProvider;
import com.cdkj.service.session.SessionUser;

/**
 * 跳转页面Controller
 * 
 * @author zhanggl10620
 * 
 */
@Controller
public class PageController {

    private static final String SESSION_KEY_USER = "user";

    @Autowired
    protected ISessionProvider sessionProvider;

    @RequestMapping(value = "/{module}.htm", method = RequestMethod.GET)
    public String indexAction(@PathVariable String module) {
        return module;
    }

    @RequestMapping(value = "/{first}/{page}.htm", method = RequestMethod.GET)
    public String commonPage1Action(@PathVariable String first,
            @PathVariable String page) {
        return first + "/" + page;
    }

    @RequestMapping(value = "/{first}/{second}/{page}.htm", method = RequestMethod.GET)
    public String commonPage2Action(@PathVariable String first,
            @PathVariable String second, @PathVariable String page) {
        return first + "/" + second + "/" + page;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String page() {
        SessionUser user = (SessionUser) sessionProvider
            .getAttribute(SESSION_KEY_USER);
        if (null == user) {
            return "redirect:security/signin.htm";
        }
        return "redirect:security/main.htm";
    }
}
