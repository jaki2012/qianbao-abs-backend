package com.qianbao.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * @author lijiechu
 * @create on 17/9/12
 * @description 用于授权的Token对象：可以增加字段提高安全性，例如时间戳、url签名
 */
public class StatelessAuthenticationToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;
    //用户id
    private long userId;

    //随机生成的uuid
    private String token;

    public StatelessAuthenticationToken(long userId, String token){
        this.userId = userId;
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
