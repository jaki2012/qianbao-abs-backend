package com.qianbao.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author lijiechu
 * @create on 17/9/12
 * @description 重写shiro登录成功后的处理器, 可用于session管理下返回json的场景
 */
public class StatelessOnLoginSuccessFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }
}
