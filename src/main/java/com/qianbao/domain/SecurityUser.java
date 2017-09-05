package com.qianbao.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author lijiechu
 * @create on 17/9/4
 * @description SpringSecurity 扩展User/增加用户ID信息
 */
public class SecurityUser extends org.springframework.security.core.userdetails.User {

    private int userID;

    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
