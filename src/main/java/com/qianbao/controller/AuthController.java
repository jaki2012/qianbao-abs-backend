package com.qianbao.controller;

import com.qianbao.common.sys.Result;
import com.qianbao.common.sys.Constants;
import com.qianbao.common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lijiechu
 * @create on 17/9/1
 * @description 用于用户登录、登出等鉴权行为
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public Result login(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam("username")String username, @RequestParam("password") String password){
        try {
            // 内部登录请求
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            // 验证
            Authentication auth = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(auth);
            request.getSession().setMaxInactiveInterval(60 * 30);
            return ResultUtil.success(Constants.USER_LOGIN_SUCCESS);
        } catch (AuthenticationException e) {
            return ResultUtil.error(403,Constants.USER_LOGIN_FAILED);
        }
    }
}
