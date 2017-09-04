package com.qianbao.service.security;

import com.qianbao.domain.Permission;
import com.qianbao.domain.User;
import com.qianbao.mapper.PermissionMapper;
import com.qianbao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijiechu
 * @create on 17/8/31
 * @description 自定义UserDetailsService
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if(null != user) {
            List<Permission> permissions = permissionMapper.findByAdminUserID(user.getUserID());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for(Permission permission: permissions){
                if( null != permission && null != permission.getPermissionName()){
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getPermissionName());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("User: " + username + " do not exist!");
        }
    }

}