package com.qianbao.controller;

import com.alibaba.fastjson.JSONObject;
import com.qianbao.common.annotation.Authorization;
import com.qianbao.common.annotation.CurrentUser;
import com.qianbao.common.sys.Result;
import com.qianbao.common.util.ResultUtil;
import com.qianbao.domain.Token;
import com.qianbao.domain.User;
import com.qianbao.mapper.UserMapper;
import com.qianbao.service.business.myinterface.TokenService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private UserMapper userMapper;

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/login")
    public Result login(@RequestParam String username, @RequestParam String password){
        User user = userMapper.findByUsername(username);
        if(null == user || !user.getPassword().equals(password)){
            return ResultUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "登录失败");
        }
        Token model = tokenService.createToken(user.getUserID());
        JSONObject result = new JSONObject();
        result.put("access_token", model.getUserId() + "_" + model.getToken());
        return ResultUtil.success(result);
    }

    @RequiresRoles("ROLE_SPV")
    @RequestMapping(value = "/logout")
    public Result logout(@CurrentUser User user){
        tokenService.deleteToken(user.getUserID());
        return ResultUtil.success("用户" + user.getUsername() + "退出成功");
    }

}
