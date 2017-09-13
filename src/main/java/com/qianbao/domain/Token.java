package com.qianbao.domain;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author lijiechu
 * @create on 17/9/12
 * @description Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 */
public class Token implements AuthenticationToken {

    private static final long serialVersionUID = 1L;
    //用户id
    private long userId;

    //随机生成的uuid
    private String token;

    public Token(long userId, String token){
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
