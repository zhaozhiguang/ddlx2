package cn.ddsxy.ddlx.support;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class AjaxUtil {

    /**
     * 是否是Ajax请求
     *
     * @param request
     * @return
     * @author SHANHY
     * @create 2017年4月4日
     */
    public static boolean isAjaxRequest(ServletRequest request) {
        String requestedWith = ((HttpServletRequest)request).getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }
}
