package cn.ddsxy.ddlx.shiro;

import cn.ddsxy.ddlx.api.AdminService;
import com.ddsxy.ddlx.model.Admin;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * shiro身份校验
 * @author zhiguang
 */
public class ShiroDbRealm extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CaptchaUserNamePassWordToken currentToken = (CaptchaUserNamePassWordToken) token;
        Admin admin = adminService.queryAdmin();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(admin.getUsername(),
                new Md5Hash(admin.getPassword(), "", 2).toHex(),this.getName());
        return info;
    }
}
