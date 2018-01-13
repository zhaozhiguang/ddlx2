package cn.ddsxy.ddlx.controller;

import cn.ddsxy.ddlx.api.UserService;
import cn.ddsxy.ddlx.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class TestController {

    @Autowired
    private UserService userService;

    //@RequiresRoles("fff")
    @RequestMapping("/index")
    @ResponseBody
    public Object index() {
        Subject subject = SecurityUtils.getSubject();
        boolean hh = subject.hasRole("hh");
        System.err.println(hh);
        return userService.getId(8);
    }

    @RequestMapping("/set")
    @ResponseBody
    public Object setId(@Valid User user, BindingResult bindingResult){
        return "拦截器校验";
    }
}
