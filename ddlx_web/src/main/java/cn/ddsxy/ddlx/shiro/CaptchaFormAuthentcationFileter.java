package cn.ddsxy.ddlx.shiro;

import cn.ddsxy.ddlx.support.AjaxUtil;
import cn.ddsxy.ddlx.support.CaptchaUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义表单过滤器
 * @author zhiguang
 */
public class CaptchaFormAuthentcationFileter extends FormAuthenticationFilter {

    /**
     * 是否开启验证码支持
     */
    private boolean captchaEbabled = true;

    /**
     * 验证码参数名
     */
    private String captchaParam = "captcha";

    public boolean isCaptchaEbabled() {
        return captchaEbabled;
    }

    public void setCaptchaEbabled(boolean captchaEbabled) {
        this.captchaEbabled = captchaEbabled;
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    public String getCaptcha(ServletRequest request){
        return WebUtils.getCleanParam(request, this.getCaptchaParam());
    }

    @Override
    protected CaptchaUserNamePassWordToken createToken(ServletRequest request, ServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String captcha = request.getParameter("captcha");
        boolean rememberMe = request.getParameter("rememberMe")!=null;
        String host = request.getRemoteHost();
        return new CaptchaUserNamePassWordToken(username, password==null?"":password, captcha, rememberMe, host);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        CaptchaUserNamePassWordToken token = createToken(request, response);
        try {
            if(captchaEbabled) CaptchaUtil.verify(request);
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (IncorrectCaptchaException e) {
            return onLoginFailure(token, e, request, response);
        } catch (UnknownAccountException e1) { // 不存在用户名对应的有效用户记录
            return onLoginFailure(token, e1, request, response);
        } catch (IncorrectCredentialsException e2) { // 用户名或密码不对
            return onLoginFailure(token, e2, request, response);
        } catch (AuthenticationException e3) { // 其它异常
            return onLoginFailure(token, e3, request, response);
        }
    }

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException e) {
        if(e instanceof IncorrectCaptchaException){
            request.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,"验证码错误");
        }else if(e instanceof UnknownAccountException){
            request.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,"用户不存在");
        }else if(e instanceof IncorrectCredentialsException){
            request.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,"密码错误");
        }else if(e instanceof AuthenticationException){
            request.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,"登录失败");
        }
    }

    /**
     * 表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，返回false表示自己已经处理了（比如重定向到另一个页面）
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                return this.executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            if(AjaxUtil.isAjaxRequest(request)){
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print("你咩有登录,跳转到登录页面");
            }else{
                this.saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }

    /**
     * 是否允许访问，返回true表示允许
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                return false;
            }
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
