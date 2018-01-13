package cn.ddsxy.ddlx.support;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring 异常处理
 */
public class SupportHandlerExceptionResolver implements HandlerExceptionResolver {


    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        System.err.println("进入异常处理-------------------------");
        e.printStackTrace();
        return null;
    }


}
