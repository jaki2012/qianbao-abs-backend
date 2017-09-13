package com.qianbao.common.shiro;

import com.qianbao.domain.Token;
import com.qianbao.domain.User;
import com.qianbao.mapper.UserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lijiechu
 * @create on 17/9/12
 * @description
 */
public class StatelessAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        // return token instanceof StatelessAuthenticationToken;
        return token instanceof Token;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //根据用户名查找角色，请根据需求实现
        long userID = (Long) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        // 可以动态添加单个角色（权限）、多个角色（权限）
        authorizationInfo.addRole(userMapper.getRoleNameByUserID(userID));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Token statelessToken = (Token)authenticationToken;

        long userID = (Long)statelessToken.getPrincipal();

        User user = userMapper.findByUserID(userID);

        if(null == user){
            return null;
        }

        // 然后进行客户端消息摘要和服务器端消息摘要的匹配
        // TODO：添加时间戳或url签名后加密进行传输
        SimpleAuthenticationInfo  authenticationInfo = new SimpleAuthenticationInfo(
                userID,
                statelessToken.getCredentials(),
                getName());
        return authenticationInfo;
    }

}
