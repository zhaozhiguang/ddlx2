package cn.ddsxy.ddlx.support;

import cn.ddsxy.ddlx.shiro.IncorrectCaptchaException;
import com.google.code.kaptcha.Constants;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 验证码
 */
public class CaptchaUtil {

    /**
     * 验证码是否正确
     * @param request
     * @return
     */
    public static void verify(ServletRequest request) throws IncorrectCaptchaException {
        //从session中取出servlet生成的验证码text值
        String expected = (String) ((HttpServletRequest)request).getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //获取用户页面输入的验证码
        String received = request.getParameter("captcha");
        System.err.println(expected+"-----------------"+received);
        if(received == null || !received.equalsIgnoreCase(expected)){
            throw new IncorrectCaptchaException();
        }
    }
}
