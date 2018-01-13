package cn.ddsxy.ddlx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SuuportController {

    @RequestMapping("")
    public String in(HttpServletRequest request, HttpSession session){
        return "index";
    }

    /**
     * 登录页面
     * @return
     */
    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }

    /**
     * 注册页面
     * @return
     */
    @RequestMapping(value = "regist", method = RequestMethod.GET)
    public String registPage() {
        return "regist";
    }

    /**
     * 注册请求
     * @return
     */
    @RequestMapping(value = "regist", method = RequestMethod.POST)
    public String regist() {

        return "regist";
    }


    /**
     * 没有权限访问
     *
     * @return
     */
    @RequestMapping("/403")
    public String errPage403() {
        return "403";
    }

    /**
     * 没找到页面404
     *
     * @return
     */
    @RequestMapping("/404")
    public String errorPage404() {
        return "404";
    }

    /**
     * 系统内部错误500
     *
     * @return
     */
    @RequestMapping("/500")
    public String errorPage500() {
        return "500";
    }

}
