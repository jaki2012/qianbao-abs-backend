package com.qianbao.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * @author lijiechu
 * @create on 17/9/12
 * @description 用于授权的Token对象：用户身份即用户名，凭证即客户端传入的消息摘要
 */
public class StatelessAuthenticationToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;
    // 用户身份即用户名
    private String username;

    // 客户端传入的密码
    private String password;

    // 凭证即客户端传入的消息摘要。
    private String clientDigest;


    public StatelessAuthenticationToken() {

    }

    public StatelessAuthenticationToken(String username, String password, String clientDigest) {
        this.username = username;
        this.password = password;
        this.clientDigest = clientDigest;
    }

    public StatelessAuthenticationToken(String username, String clientDigest) {
        super();
        this.username = username;
        this.clientDigest = clientDigest;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return clientDigest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientDigest() {
        return clientDigest;
    }

    public void setClientDigest(String clientDigest) {
        this.clientDigest = clientDigest;
    }
}
